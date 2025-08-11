package com.softwarecleandevelopment.cryptowallet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.RecoveryPhraseScreen
import com.softwarecleandevelopment.cryptowallet.useragreement.presentation.UserAgreementScreen

@Composable
fun CryptoWalletNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = AppScreens.UserAgreementScreen.route,
    ) {
        composable(route = AppScreens.UserAgreementScreen.route) {
            UserAgreementScreen(
                onContinueClicked = {
                    navController.navigate(AppScreens.RecoveryPhraseScreen.route)
                }
            )
        }
        composable(route = AppScreens.RecoveryPhraseScreen.route) {
            RecoveryPhraseScreen()
        }
    }
}