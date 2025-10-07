package com.softwarecleandevelopment.feature.wallet_home.data.mapper

import com.softwarecleandevelopment.feature.wallet_home.data.datasource.ChartCoinDto
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartCoin
import com.softwarecleandevelopment.feature.wallet_home.domain.models.CoinLinks

fun ChartCoinDto.toDomain(): ChartCoin {
    val md = marketData
    val priceUsd = md?.currentPrice?.get("usd") ?: 0.0
    val change = md?.priceChangePercentage24h
    val marketCap = md?.marketCap?.get("usd")
    val circulatingSupply = md?.circulatingSupply
    val total = md?.totalSupply
    val spark = md?.sparkline7d?.price ?: emptyList()
    val links = listOf(
        CoinLinks(
            "CoinGecko",
            "https://www.coingecko.com/en/coins/${id}"
        )
    )
    return ChartCoin(
        id = id,
        symbol = symbol,
        name = name,
        priceUsd = priceUsd,
        changePercent24Hr = change,
        marketCapUsd = marketCap,
        circulatingSupply = circulatingSupply,
        totalSupply = total,
        sparkline = spark,
        links = links
    )
}