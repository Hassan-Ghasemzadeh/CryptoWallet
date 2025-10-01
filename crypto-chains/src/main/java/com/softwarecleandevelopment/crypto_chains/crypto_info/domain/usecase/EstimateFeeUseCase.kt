package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.FeeEstimationParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.repository.CryptoInfoRepository
import javax.inject.Inject

class EstimateFeeUseCase @Inject constructor(
    private val repository: CryptoInfoRepository,
) : UseCase<Double, FeeEstimationParams>() {
    override suspend fun invoke(params: FeeEstimationParams): Double {
        val result = repository.getFee(params)
        return when (result) {
            is Resource.Error -> -1.0
            is Resource.Loading -> 0.0
            is Resource.Success<Double> -> result.data
        }
    }
}