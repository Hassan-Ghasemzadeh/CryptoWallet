package com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.repository.EthRepository
import javax.inject.Inject

class EstimateNetworkFeeUseCase @Inject constructor(val repository: EthRepository) :
    UseCase<Double, String?>() {
    override suspend fun invoke(params: String?): Double {
        val result = repository.estimateNetworkFee(params)
        return when (result) {
            is Resource.Error -> -1.0
            is Resource.Loading -> 0.0
            is Resource.Success<Double> -> result.data
        }
    }
}