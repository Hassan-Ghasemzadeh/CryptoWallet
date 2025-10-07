package com.softwarecleandevelopment.feature.wallet_home.domain.models

data class ChartCoin(
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: Double,
    val changePercent24Hr: Double?,
    val marketCapUsd: Double?,
    val circulatingSupply: Double?,
    val totalSupply: Double?,
    val sparkline: List<Double> = emptyList(),
    val links: List<CoinLinks> = emptyList(),
) {
    companion object {
        fun mock(
            id: String = "bitcoin",
            name: String = "Bitcoin",
            symbol: String = "BTC"
        ): ChartCoin = ChartCoin(
            id = id,
            symbol = symbol,
            name = name,
            priceUsd = 65000.00,
            changePercent24Hr = 1.5,
            marketCapUsd = 1_280_000_000_000.0,
            circulatingSupply = 19_800_000.0,
            totalSupply = 21_000_000.0,
            sparkline = listOf(64000.0, 65500.0, 64500.0, 65000.0, 64800.0),
            links = listOf(
                CoinLinks(
                    "CoinGecko",
                    "https://www.coingecko.com/en/coins/${id}"
                )
            ) // Assuming CoinLinks isn't needed for a simple mock
        )
    }
}

data class CoinLinks(
    val title: String,
    val url: String,
)
