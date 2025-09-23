package com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource

import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger


interface EthDatasource {

    fun generateAddress(): Flow<String?>

    suspend fun getEthBalance(rpcUrl: String, address: String): Double


    suspend fun send(
        params: SendTokenEvent
    ): SendResult

    suspend fun estimateNetworkFee(
        tokenContractAddress: String? = null
    ): Pair<BigInteger /*gasPrice*/, BigInteger /*gasLimit*/>

}