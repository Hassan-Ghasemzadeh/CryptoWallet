package com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import kotlinx.coroutines.flow.Flow

interface CryptoInfoDatasource {
    fun getCryptoInfo(
        userAddress: String
    ): Flow<List<CryptoInfo>>

    suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double
}