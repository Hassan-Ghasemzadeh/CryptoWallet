package com.softwarecleandevelopment.feature.wallet_home.data.datasource

import com.google.gson.annotations.SerializedName

data class ChartCoinDto(
    // These fields are usually guaranteed to exist.
    val id: String,
    val symbol: String,
    val name: String,
    // marketData might be null if the API call fails partially or data is missing.
    @SerializedName("market_data") val marketData: MarketDataDto? = null,
) {
    data class MarketDataDto(
        // Use default empty map/list if data might be missing,
        // rather than relying on a potentially nullable map/list.

        // Example: "current_price": {"usd": 65000.0, "eur": 60000.0}
        @SerializedName("current_price") val currentPrice: Map<String, Double> = emptyMap(),

        @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double? = null,

        // Example: "market_cap": {"usd": 1200000000000.0}
        @SerializedName("market_cap") val marketCap: Map<String, Double> = emptyMap(),

        @SerializedName("circulating_supply") val circulatingSupply: Double? = null,
        @SerializedName("total_supply") val totalSupply: Double? = null,

        @SerializedName("sparkline_7d") val sparkline7d: SparklineDto? = null,
    )

    data class SparklineDto(
        // Sparkline is a list of prices. Default to an empty list.
        val price: List<Double> = emptyList()
    )
}