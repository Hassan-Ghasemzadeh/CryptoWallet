package com.softwarecleandevelopment.feature.wallet_home.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendNavigationParams(
    val balance: Double,
    val coin: String,
) : Parcelable
