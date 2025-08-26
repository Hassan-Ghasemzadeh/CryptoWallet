package com.softwarecleandevelopment.feature.wallets.domain.models

sealed class DeleteWalletEvent {
    object NavigateToCreateWallet : DeleteWalletEvent()
}