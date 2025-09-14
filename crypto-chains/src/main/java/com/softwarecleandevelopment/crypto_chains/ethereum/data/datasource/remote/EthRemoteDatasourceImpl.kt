package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote

import android.util.Log
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthEstimateGas
import org.web3j.protocol.core.methods.response.EthGetTransactionCount
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

class EthRemoteDatasourceImpl @Inject constructor(val ethApi: EthApi) : EthRemoteDatasource {
    private val ethRpcUrl = "https://mainnet.infura.io/v3/ce064e40a69b4971a4e28afcb113baa0"
    override suspend fun getPrice(
        ids: String, change: Boolean
    ): Map<String, Map<String, Double>> {
        return ethApi.getPrice(ids, change = change)
    }

    override suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double {
        return when (symbol) {
            "ETH" -> getEthBalance(ethRpcUrl, userAddress)
            else -> {
                TODO("Not yet implemented")
            }
        }
    }

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

    override suspend fun send(params: SendTokenEvent): SendResult {
        return withContext(Dispatchers.IO) {
            val web3 = Web3j.build(HttpService(params.rpcUrl))

            // credentials from private key (DO NOT store plain text in real apps)
            val credentials = Credentials.create(params.privateKey)
            val fromAddress = credentials.address

            // get nonce
            val ethGetTransactionCount: EthGetTransactionCount = web3.ethGetTransactionCount(
                fromAddress, DefaultBlockParameterName.PENDING
            ).send()
            val nonce = ethGetTransactionCount.transactionCount

            // get gas price
            val gasPrice = web3.ethGasPrice().send().gasPrice

            // build data and value
            val rawTx: RawTransaction
            val value: BigInteger
            val gasLimit: BigInteger

            if (params.tokenContractAddress.isNullOrEmpty()) {
                // native transfer (ETH/BNB)
                // convert amountHuman to Wei
                value = Convert.toWei(params.amountHuman, Convert.Unit.ETHER).toBigInteger()

                // estimate gas
                val txForEstimate = Transaction.createEtherTransaction(
                    fromAddress, null, gasPrice, null, params.toAddress, value
                )
                val ethEstimateGas: EthEstimateGas = web3.ethEstimateGas(txForEstimate).send()
                gasLimit = ethEstimateGas.amountUsed ?: BigInteger.valueOf(21000L)

                rawTx = RawTransaction.createEtherTransaction(
                    nonce, gasPrice, gasLimit, params.toAddress, value
                )
            } else {
                // ERC20 transfer: build function call data
                // function signature: transfer(address,uint256)
                val scaledAmount =
                    params.amountHuman.multiply(BigDecimal.TEN.pow(params.tokenDecimals))
                        .toBigInteger()

                // build ERC20 transfer data manually
                val methodId = Numeric.hexStringToByteArray(
                    org.web3j.crypto.Hash.sha3String("transfer(address,uint256)")
                        .substring(0, 10)
                )
                val function = org.web3j.abi.datatypes.Function(
                    "transfer", listOf(
                        org.web3j.abi.datatypes.Address(params.toAddress),
                        org.web3j.abi.datatypes.generated.Uint256(scaledAmount)
                    ), emptyList()
                )
                val encodedFunction = org.web3j.abi.FunctionEncoder.encode(function)

                // estimate gas via eth_estimateGas
                val tx = Transaction.createFunctionCallTransaction(
                    fromAddress,
                    null,
                    gasPrice,
                    null,
                    params.tokenContractAddress,
                    encodedFunction
                )
                val ethEstimateGas: EthEstimateGas = web3.ethEstimateGas(tx).send()
                gasLimit = ethEstimateGas.amountUsed ?: BigInteger.valueOf(100_000L)

                rawTx = RawTransaction.createTransaction(
                    nonce, gasPrice, gasLimit, params.tokenContractAddress, encodedFunction
                )
                value = BigInteger.ZERO
            }

            // sign transaction
            val signedMessage =
                TransactionEncoder.signMessage(rawTx, params.chainId, credentials)
            val hexValue = Numeric.toHexString(signedMessage)

            // send
            val ethSend = web3.ethSendRawTransaction(hexValue).send()

            val txHash = ethSend.transactionHash

            // optionally wait for receipt (sync wait) - here we try for a few attempts
            var receipt: TransactionReceipt? = null
            repeat(15) {
                val request = web3.ethGetTransactionReceipt(txHash).send()
                if (request.transactionReceipt.isPresent) {
                    receipt = request.transactionReceipt.get()
                    return@repeat
                }
                delay(2000)
            }
            return@withContext SendResult(txHash, receipt)
        }
    }

    override suspend fun estimateNetworkFee(
        tokenContractAddress: String?
    ): Pair<BigInteger, BigInteger> = withContext(Dispatchers.IO) {
       val rpcUrl = "https://mainnet.infura.io/v3/ce064e40a69b4971a4e28afcb113baa0"
        val web3 = Web3j.build(HttpService(rpcUrl))
        val gasPrice = web3.ethGasPrice().send().gasPrice
        val gasLimit =
            if (tokenContractAddress.isNullOrEmpty()) BigInteger.valueOf(21_000L) else BigInteger.valueOf(
                100_000L
            )
        Pair(gasPrice, gasLimit)
    }
}