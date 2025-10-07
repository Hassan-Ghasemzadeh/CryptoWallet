package com.softwarecleandevelopment.feature.wallet_home.data.datasource

import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartCoin
import kotlinx.coroutines.flow.Flow

interface ChartCoinDataSource {
    suspend fun fetchChartCoin(id: String): ChartCoin
    fun observeLivePriceFor(assets: Set<String>): Flow<Map<String, Double>>
    fun clear()
}