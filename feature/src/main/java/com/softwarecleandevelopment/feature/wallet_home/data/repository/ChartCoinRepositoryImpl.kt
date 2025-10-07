package com.softwarecleandevelopment.feature.wallet_home.data.repository

import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.safeCall
import com.softwarecleandevelopment.core.common.utils.safeFlowCall
import com.softwarecleandevelopment.feature.wallet_home.data.datasource.ChartCoinDataSource
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartCoin
import com.softwarecleandevelopment.feature.wallet_home.domain.repository.ChartCoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChartCoinRepositoryImpl @Inject constructor(
    private val datasource: ChartCoinDataSource
) : ChartCoinRepository {
    override suspend fun fetchChartCoin(id: String): Resource<ChartCoin> {
        return safeCall { datasource.fetchChartCoin(id) }
    }

    override fun observeLivePriceFor(assets: Set<String>): Resource<Flow<Map<String, Double>>> {
        return safeFlowCall { datasource.observeLivePriceFor(assets) }
    }

    override fun clear() {
        return datasource.clear()
    }
}