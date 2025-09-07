package com.softwarecleandevelopment.feature.wallet_home.domain.models

data class BarcodeResult(
    val rawValue: String,
    val displayValue: String? = null,
    val format: Int
)