package com.softwarecleandevelopment.crypto_chains.crypto_info.data.datasource

import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import kotlinx.coroutines.flow.Flow

interface CryptoInfoDatasource {
    fun getCryptoInfo(
        params: AddressParams,
    ): Flow<List<CryptoInfo>>

    suspend fun getBalance(
        symbol: String, userAddress: String
    ): Double
}