package com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.remote

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import kotlinx.coroutines.flow.Flow

interface EthCryptoRemoteRepository {
    fun getCryptoInfo(
        cryptos: List<CryptoInfo>,
        userAddress: String
    ): Resource<Flow<List<CryptoInfo>>>
}