package com.softwarecleandevelopment.feature.wallet_home.domain.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartCoin
import kotlinx.coroutines.flow.Flow

interface ChartCoinRepository {
    suspend fun fetchChartCoin(id: String): Resource<ChartCoin>
    fun observeLivePriceFor(assets: Set<String>): Resource<Flow<Map<String, Double>>>
    fun clear()
}