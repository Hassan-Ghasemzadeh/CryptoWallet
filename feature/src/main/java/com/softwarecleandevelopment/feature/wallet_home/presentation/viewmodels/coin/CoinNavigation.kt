package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.coin

import com.softwarecleandevelopment.feature.wallet_home.domain.models.ReceiveNavigationParams
import com.softwarecleandevelopment.feature.wallet_home.domain.models.SendNavigationParams

sealed class CoinNavigation {
    data class NavigateToReceive(val data: ReceiveNavigationParams) : CoinNavigation()
    data class NavigateToSend(val data: SendNavigationParams) : CoinNavigation()
    object OnSwapClicked : CoinNavigation()
}