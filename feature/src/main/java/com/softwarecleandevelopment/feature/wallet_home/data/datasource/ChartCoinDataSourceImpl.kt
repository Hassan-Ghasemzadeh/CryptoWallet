package com.softwarecleandevelopment.feature.wallet_home.data.datasource

import com.softwarecleandevelopment.feature.wallet_home.data.mapper.toDomain
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartCoin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChartCoinDataSourceImpl @Inject constructor(
    private val api: ChartCoinApi,
    private val socketManager: LivePriceSocketManager,
) : ChartCoinDataSource {
    override suspend fun fetchChartCoin(id: String): ChartCoin {
        val dto = api.getChartDetail(id)
        return dto.toDomain()
    }

    override fun observeLivePriceFor(assets: Set<String>): Flow<Map<String, Double>> {
        socketManager.connectForAssets(assets)
        return socketManager.prices
    }

    override fun clear() {
        socketManager.disconnect()
    }

}