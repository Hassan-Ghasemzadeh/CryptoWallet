package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive

import android.graphics.Bitmap

data class ReceiveCoinUiState(
    val title: String = "Receive ETH",
    val walletName: String = "",
    val address: String = "",
    val qr: Bitmap? = null,
    val toastMessage: String? = null
)

sealed interface ReceiveCoinEvent {
    data object OnBackClick : ReceiveCoinEvent
    data object OnCopyClick : ReceiveCoinEvent
    data object OnShareClick : ReceiveCoinEvent
}