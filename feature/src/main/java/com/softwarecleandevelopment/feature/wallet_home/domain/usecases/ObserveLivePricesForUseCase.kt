package com.softwarecleandevelopment.feature.wallet_home.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.repository.ChartCoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ObserveLivePricesForUseCase @Inject constructor(
    private val repository: ChartCoinRepository,
) : UseCase<Flow<Map<String, Double>>, Set<String>>() {
    override suspend fun invoke(params: Set<String>): Flow<Map<String, Double>> {
        val result = repository.observeLivePriceFor(params)
        return when (result) {
            is Resource.Error -> emptyFlow()
            is Resource.Loading -> emptyFlow()
            is Resource.Success<Flow<Map<String, Double>>> -> result.data
        }
    }
}