package com.softwarecleandevelopment.crypto_chains.dogecoin.domain.usecase

import com.softwarecleandevelopment.core.common.utils.BalanceUseCase
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.dogecoin.domain.repository.DogeCoinRepository
import javax.inject.Inject

class GetDogeCoinBalanceUseCase @Inject constructor(
    private val repository: DogeCoinRepository
) : UseCase<Double, String>(), BalanceUseCase {
    override suspend fun invoke(params: String): Double {
        val result = repository.getDogeCoinBalance(params)
        return when (result) {
            is Resource.Error -> -1.0
            is Resource.Loading -> 0.0
            is Resource.Success<Double> -> result.data
        }
    }
}