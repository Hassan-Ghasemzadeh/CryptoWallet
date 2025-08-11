package com.softwarecleandevelopment.cryptowallet.navigation

sealed class AppScreens(val route: String) {
    object UserAgreementScreen : AppScreens("user_agreement_screen")
    object RecoveryPhraseScreen : AppScreens("recovery_phrase_screen")
}