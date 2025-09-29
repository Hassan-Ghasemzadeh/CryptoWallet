package com.softwarecleandevelopment.crypto_chains.dogecoin.domain.usecase

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.dogecoin.domain.repository.DogeCoinRepository
import javax.inject.Inject

class EstimateDogeFeeUseCase @Inject constructor(
    private val repository: DogeCoinRepository,
) : UseCase<Double, String>() {
    override suspend fun invoke(params: String): Double {
        val fee = repository.estimateFee(params)
        return when (fee) {
            is Resource.Error -> -1.0
            is Resource.Loading -> 0.0
            is Resource.Success<Double> -> fee.data
        }
    }
}