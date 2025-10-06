package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive

import com.softwarecleandevelopment.feature.wallet_home.domain.models.ReceiveNavigationParams
import com.softwarecleandevelopment.feature.wallet_home.domain.models.SendNavigationParams


sealed class CoinNavigation {
    data class NavigateToReceive(val data: ReceiveNavigationParams) : CoinNavigation()
    data class NavigateToSend(val data: SendNavigationParams) : CoinNavigation()
}
