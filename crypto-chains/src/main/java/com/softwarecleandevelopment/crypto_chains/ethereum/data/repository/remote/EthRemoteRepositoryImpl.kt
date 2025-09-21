package com.softwarecleandevelopment.crypto_chains.ethereum.data.repository.remote

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.crypto_chains.ethereum.data.datasource.remote.EthRemoteDatasource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote.EthRemoteRepository
import java.math.BigInteger
import javax.inject.Inject

class EthRemoteRepositoryImpl @Inject constructor(private val datasource: EthRemoteDatasource) :
    EthRemoteRepository {
    override suspend fun send(params: SendTokenEvent): Resource<SendResult> {
        return safeCall { datasource.send(params) }
    }

    override suspend fun estimateNetworkFee(
        tokenContractAddress: String?
    ): Resource<Pair<BigInteger, BigInteger>> {
        return safeCall { datasource.estimateNetworkFee(tokenContractAddress) }
    }
}