package com.softwarecleandevelopment.crypto_chains.ethereum.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.EthDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger
import javax.inject.Inject

class EthRepositoryImpl @Inject constructor(private val datasource: EthDatasource,) :
    EthRepository {
    override fun generateAddress(): Resource<Flow<String?>> {
        return safeFlowCall { datasource.generateAddress() }
    }

    override suspend fun send(params: SendTokenEvent): Resource<SendResult> {
        return safeCall { datasource.send(params) }
    }

    override suspend fun estimateNetworkFee(
        tokenContractAddress: String?
    ): Resource<Double> {
        return safeCall { datasource.estimateNetworkFee(tokenContractAddress) }
    }
}