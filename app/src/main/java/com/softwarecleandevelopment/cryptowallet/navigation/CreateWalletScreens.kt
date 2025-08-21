package com.softwarecleandevelopment.cryptowallet.navigation

sealed class CreateWalletScreens(val route: String) {
    object UserAgreementScreen : CreateWalletScreens("user_agreement_screen")
    object RecoveryPhraseScreen : CreateWalletScreens("recovery_phrase_screen")
    object ConfirmPhraseScreen : CreateWalletScreens("confirm_phrase_screen")
    object LandingScreen : CreateWalletScreens("landing_screen")
}