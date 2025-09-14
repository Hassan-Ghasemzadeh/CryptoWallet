package com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import kotlinx.coroutines.flow.Flow

interface EthRemoteRepository {
    fun getCryptoInfo(
        cryptos: List<CryptoInfo>,
        userAddress: String
    ): Resource<Flow<List<CryptoInfo>>>
}