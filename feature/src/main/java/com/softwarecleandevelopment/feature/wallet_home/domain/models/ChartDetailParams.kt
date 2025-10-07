package com.softwarecleandevelopment.feature.wallet_home.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChartDetailParams(
    val coinId: String,
    val asset: String,
) : Parcelable
