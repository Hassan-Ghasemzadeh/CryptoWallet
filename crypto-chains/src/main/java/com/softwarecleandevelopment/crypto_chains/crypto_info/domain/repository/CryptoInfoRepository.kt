package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CoinInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.FeeEstimationParams
import kotlinx.coroutines.flow.Flow

interface CryptoInfoRepository {
    fun getCryptoInfo(
        params: AddressParams,
    ): Resource<Flow<List<CoinInfo>>>

    suspend fun getFee(params: FeeEstimationParams): Resource<Double>
}