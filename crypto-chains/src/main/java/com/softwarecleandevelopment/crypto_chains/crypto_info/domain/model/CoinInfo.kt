package com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model

import com.softwarecleandevelopment.core.common.utils.AddressGenerator
import kotlinx.serialization.Serializable

@Serializable
data class CoinInfo(
    val id: String,
    val symbol: String,
    val name: String,
    val iconRes: Int,
    val priceUsd: Double = 0.0,
    val changePrecent: Double = 0.0,
    val balance: Double = 0.0,
    val generator: AddressGenerator,
    val updatedAt: Long = System.currentTimeMillis()
)