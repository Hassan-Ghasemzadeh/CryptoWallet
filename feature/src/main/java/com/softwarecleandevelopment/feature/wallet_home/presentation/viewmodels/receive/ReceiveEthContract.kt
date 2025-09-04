package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive

import android.graphics.Bitmap

data class ReceiveEthUiState(
    val title: String = "Receive ETH",
    val walletName: String = "",
    val address: String = "",
    val qr: Bitmap? = null,
    val toastMessage: String? = null
)

sealed interface ReceiveEthEvent {
    data object OnBackClick : ReceiveEthEvent
    data object OnCopyClick : ReceiveEthEvent
    data object OnShareClick : ReceiveEthEvent
}