package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource

import com.softwarecleandevelopment.core.common.utils.Constants
import com.softwarecleandevelopment.core.common.utils.FeeEstimator
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Convert
import java.math.BigInteger
import javax.inject.Inject

class EthFeeEstimator @Inject constructor() : FeeEstimator {
    override fun estimateFee(feeRate: Long, address: String): Double {
        val web3j = Web3j.build(HttpService(Constants.rpcUrl))
        val gasPrice = web3j.ethGasPrice().send().gasPrice
        val gasLimit = getGasLimit(address)
        val feeInWei = gasPrice.multiply(gasLimit)
        val feeInEther = Convert.fromWei(feeInWei.toBigDecimal(), Convert.Unit.ETHER)
        return feeInEther.toDouble()
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