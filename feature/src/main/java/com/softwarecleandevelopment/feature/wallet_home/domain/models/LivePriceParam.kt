package com.softwarecleandevelopment.feature.wallet_home.domain.models


data class LivePriceParam(
    val id: String,
    val vsCurrency: String = "usd",
    val days: String,
    val interval: String

)
