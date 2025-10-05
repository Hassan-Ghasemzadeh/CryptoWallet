package com.softwarecleandevelopment.core.crypto.models

import com.softwarecleandevelopment.core.common.utils.AddressGenerator
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class CoinInfo(
    val id: String,
    val symbol: String,
    val name: String,
    val iconRes: Int,
    val priceUsd: Double = 0.0,
    val changePrecent: Double = 0.0,
    val balance: Double = 0.0,
    @Transient
    val generator: AddressGenerator? = null,
    val updatedAt: Long = System.currentTimeMillis()
)