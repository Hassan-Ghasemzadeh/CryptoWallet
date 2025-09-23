package com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger

interface EthRepository {

    fun generateAddress(): Resource<Flow<String?>>

    suspend fun send(
        params: SendTokenEvent
    ): Resource<SendResult>

    suspend fun estimateNetworkFee(
        tokenContractAddress: String? = null
    ): Resource<Pair<BigInteger /*gasPrice*/, BigInteger /*gasLimit*/>>

}