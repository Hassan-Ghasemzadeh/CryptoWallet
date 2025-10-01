package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.FeeEstimationParams
import kotlinx.coroutines.flow.Flow

interface CryptoInfoRepository {
    fun getCryptoInfo(
        params: AddressParams,
    ): Resource<Flow<List<CryptoInfo>>>

    suspend fun getFee(params: FeeEstimationParams): Resource<Double>
}