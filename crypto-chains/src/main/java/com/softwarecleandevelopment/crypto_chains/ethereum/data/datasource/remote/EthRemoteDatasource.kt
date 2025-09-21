package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote

import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import java.math.BigInteger


interface EthRemoteDatasource {
    suspend fun getEthBalance(rpcUrl: String, address: String): Double


    suspend fun send(
        params: SendTokenEvent
    ): SendResult

    suspend fun estimateNetworkFee(
        tokenContractAddress: String? = null
    ): Pair<BigInteger /*gasPrice*/, BigInteger /*gasLimit*/>

}