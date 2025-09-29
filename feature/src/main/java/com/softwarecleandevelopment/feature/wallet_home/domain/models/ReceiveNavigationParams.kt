package com.softwarecleandevelopment.feature.wallet_home.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceiveNavigationParams(
    val address: String,
    val title: String,
) : Parcelable
