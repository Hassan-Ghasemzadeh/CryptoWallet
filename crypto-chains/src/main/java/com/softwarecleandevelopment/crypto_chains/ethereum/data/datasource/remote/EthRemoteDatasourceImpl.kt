package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote

import android.util.Log
import com.softwarecleandevelopment.core.common.utils.Constants
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.TransactionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthEstimateGas
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

private const val ETH_TRANSFER_GAS_LIMIT = 21_000L
private const val ERC20_TRANSFER_GAS_LIMIT = 100_000L
private const val RECEIPT_POLLING_ATTEMPTS = 15
private const val RECEIPT_POLLING_DELAY_MS = 2000L

class EthRemoteDatasourceImpl @Inject constructor() : EthRemoteDatasource {
    val rpcUrl = Constants.rpcUrl
    override suspend fun getEthBalance(
        rpcUrl: String, address: String
    ): Double {
        return withContext(Dispatchers.IO) {
            try {
                val web3 = Web3j.build(HttpService(rpcUrl))
                val balanceWei =
                    web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
                balanceWei.toBigDecimal().divide(BigDecimal.TEN.pow(18)).toDouble()
            } catch (e: Exception) {
                Log.d("EthCryptoDatasourceImpl", "getEthBalance: ${e.message}")
                0.0
            }
        }
    }

    /**
     * Sends a transaction on the blockchain, either a native currency transfer or an ERC20 token transfer.
     * This function handles the entire transaction lifecycle, from building to sending and waiting for a receipt.
     *
     * @param params The parameters for the transaction.
     * @return A [SendResult] containing the transaction hash and the final receipt.
     */
    override suspend fun send(params: SendTokenEvent): SendResult {
        return withContext(Dispatchers.IO) {
            val web3 = Web3j.build(HttpService(rpcUrl))
            val credentials = Credentials.create(params.privateKey)
            val fromAddress = credentials.address

            val gasPrice = web3.ethGasPrice().send().gasPrice
            val nonce = getTransactionCount(web3, fromAddress)

            val transactionData = if (params.tokenContractAddress.isNullOrEmpty()) {
                buildNativeTransferData(web3, fromAddress, params, gasPrice, nonce)
            } else {
                buildErc20TransferData(web3, fromAddress, params, gasPrice, nonce)
            }

            val signedTransaction =
                signTransaction(transactionData.rawTx, params.chainId, credentials)
            val txHash = sendSignedTransaction(web3, signedTransaction)
            val receipt = waitForTransactionReceipt(web3, txHash)

            SendResult(txHash, receipt)
        }
    }

    /**
     * Retrieves the transaction count (nonce) for a given address.
     */
    private suspend fun getTransactionCount(web3: Web3j, fromAddress: String): BigInteger {
        val ethGetTransactionCount = web3.ethGetTransactionCount(
            fromAddress,
            DefaultBlockParameterName.PENDING
        ).send()
        return ethGetTransactionCount.transactionCount
    }

    /**
     * Builds the necessary data for a native currency transfer.
     */
    private fun buildNativeTransferData(
        web3: Web3j,
        fromAddress: String,
        params: SendTokenEvent,
        gasPrice: BigInteger,
        nonce: BigInteger
    ): TransactionData {
        val valueInWei = Convert.toWei(params.amountHuman, Convert.Unit.ETHER).toBigInteger()
        val gasLimit = estimateGasLimitForNativeTransfer(
            web3,
            fromAddress,
            params.toAddress,
            valueInWei,
            gasPrice
        )

        val rawTx = RawTransaction.createEtherTransaction(
            nonce,
            gasPrice,
            gasLimit,
            params.toAddress,
            valueInWei
        )
        return TransactionData(rawTx, valueInWei)
    }

    /**
     * Builds the necessary data for an ERC20 token transfer.
     */
    private fun buildErc20TransferData(
        web3: Web3j,
        fromAddress: String,
        params: SendTokenEvent,
        gasPrice: BigInteger,
        nonce: BigInteger
    ): TransactionData {
        val scaledAmount = params.amountHuman
            .multiply(BigDecimal.TEN.pow(params.tokenDecimals))
            .toBigInteger()

        val encodedFunction = encodeErc20TransferFunction(params.toAddress, scaledAmount)
        val gasLimit = estimateGasLimitForErc20Transfer(
            web3,
            fromAddress,
            params.tokenContractAddress!!,
            encodedFunction,
            gasPrice
        )

        val rawTx = RawTransaction.createTransaction(
            nonce,
            gasPrice,
            gasLimit,
            params.tokenContractAddress,
            BigInteger.ZERO,
            encodedFunction
        )
        return TransactionData(rawTx, BigInteger.ZERO)
    }

    /**
     * Encodes the ERC20 transfer function call.
     */
    private fun encodeErc20TransferFunction(toAddress: String, scaledAmount: BigInteger): String {
        val function = org.web3j.abi.datatypes.Function(
            "transfer",
            listOf(
                Address(toAddress),
                Uint256(scaledAmount)
            ),
            emptyList()
        )
        return FunctionEncoder.encode(function)
    }

    /**
     * Estimates the gas limit for a native transfer.
     */
    private fun estimateGasLimitForNativeTransfer(
        web3: Web3j,
        fromAddress: String,
        toAddress: String,
        valueInWei: BigInteger,
        gasPrice: BigInteger
    ): BigInteger {
        val txForEstimate = Transaction.createEtherTransaction(
            fromAddress,
            null,
            gasPrice,
            null,
            toAddress,
            valueInWei
        )
        val ethEstimateGas: EthEstimateGas = web3.ethEstimateGas(txForEstimate).send()
        return ethEstimateGas.amountUsed ?: BigInteger.valueOf(ETH_TRANSFER_GAS_LIMIT)
    }

    /**
     * Estimates the gas limit for an ERC20 transfer.
     */
    private fun estimateGasLimitForErc20Transfer(
        web3: Web3j,
        fromAddress: String,
        tokenContractAddress: String,
        encodedFunction: String,
        gasPrice: BigInteger
    ): BigInteger {
        val tx = Transaction.createFunctionCallTransaction(
            fromAddress,
            null,
            gasPrice,
            null,
            tokenContractAddress,
            encodedFunction
        )
        val ethEstimateGas: EthEstimateGas = web3.ethEstimateGas(tx).send()
        return ethEstimateGas.amountUsed ?: BigInteger.valueOf(ERC20_TRANSFER_GAS_LIMIT)
    }

    /**
     * Signs a raw transaction with the user's credentials.
     */
    private fun signTransaction(
        rawTx: RawTransaction,
        chainId: Long,
        credentials: Credentials
    ): String {
        val signedMessage = TransactionEncoder.signMessage(rawTx, chainId, credentials)
        return Numeric.toHexString(signedMessage)
    }

    /**
     * Sends a signed transaction to the blockchain.
     */
    private fun sendSignedTransaction(web3: Web3j, signedTransaction: String): String {
        val ethSend = web3.ethSendRawTransaction(signedTransaction).send()
        return ethSend.transactionHash
    }

    /**
     * Polls the network to wait for a transaction receipt.
     */
    private suspend fun waitForTransactionReceipt(
        web3: Web3j,
        txHash: String
    ): TransactionReceipt? {
        repeat(RECEIPT_POLLING_ATTEMPTS) {
            val request = web3.ethGetTransactionReceipt(txHash).send()
            if (request.transactionReceipt.isPresent) {
                return request.transactionReceipt.get()
            }
            delay(RECEIPT_POLLING_DELAY_MS)
        }
        return null
    }

    override suspend fun estimateNetworkFee(tokenContractAddress: String?): Pair<BigInteger, BigInteger> =
        withContext(Dispatchers.IO) {
            val web3j = Web3j.build(HttpService(rpcUrl))
            val gasPrice = web3j.ethGasPrice().send().gasPrice
            val gasLimit = getGasLimit(tokenContractAddress)
            gasPrice to gasLimit
        }

    private fun getGasLimit(tokenContractAddress: String?): BigInteger {
        val defaultGasLimit = BigInteger.valueOf(21_000L)
        val tokenTransferGasLimit = BigInteger.valueOf(100_000L)
        return if (tokenContractAddress.isNullOrEmpty()) {
            defaultGasLimit
        } else {
            tokenTransferGasLimit
        }
    }
}