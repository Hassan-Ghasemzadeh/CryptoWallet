package com.softwarecleandevelopment.crypto_chains.tether.domain.usecase

import com.softwarecleandevelopment.core.common.utils.BalanceUseCase
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.crypto_chains.tether.domain.repository.TetherRepository
import javax.inject.Inject

class GetTetherBalanceUseCase @Inject constructor(
    private val repository: TetherRepository
) : UseCase<Double, String>(), BalanceUseCase {
    override suspend fun invoke(params: String): Double {
        val result = repository.getTetherBalance(params)
        return when (result) {
            is Resource.Error -> -1.0
            is Resource.Loading -> 0.0
            is Resource.Success<Double> -> result.data
        }
    }
}