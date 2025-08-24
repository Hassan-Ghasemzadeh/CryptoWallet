package com.softwarecleandevelopment.feature.wallet_home.domain.models

import androidx.compose.ui.graphics.Color

data class Coin(
    val name: String,
    val symbol: String,
    val price: String,
    val changePercent: Double,
    val symbolAmount: String,
    val tint: Color
)

val sampleCoins = listOf(
    Coin("Bitcoin", "BTC", "115,777.00", 2.24, "0 BTC", Color(0xFFF7931A)),
    Coin("Ethereum", "ETH", "4,713.59", 8.53, "0 ETH", Color(0xFF3C3C3D)),
    Coin("BNB Chain", "BNB", "888.54", 4.06, "0 BNB", Color(0xFFF3BA2F)),
    Coin("Solana", "SOL", "204.65", 10.76, "0 SOL", Color(0xFF14F195))
)