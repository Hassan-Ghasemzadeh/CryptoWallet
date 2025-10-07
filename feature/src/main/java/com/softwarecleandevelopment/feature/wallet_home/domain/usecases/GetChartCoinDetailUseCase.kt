package com.softwarecleandevelopment.feature.wallet_home.domain.usecases

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartCoin
import com.softwarecleandevelopment.feature.wallet_home.domain.repository.ChartCoinRepository
import javax.inject.Inject

class GetChartCoinDetailUseCase @Inject constructor(
    private val repository: ChartCoinRepository
) : UseCase<ChartCoin, String>() {
    override suspend fun invoke(params: String): ChartCoin {
        val result = repository.fetchChartCoin(params)
        return when (result) {
            is Resource.Error -> ChartCoin.mock()
            is Resource.Loading -> ChartCoin.mock()
            is Resource.Success<ChartCoin> -> result.data
        }
    }
}