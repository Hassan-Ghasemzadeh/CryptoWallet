package com.softwarecleandevelopment.cryptowallet.navigation

import com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.ConfirmPhraseScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softwarecleandevelopment.cryptowallet.landing.LandingScreen
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.RecoveryPhraseScreen
import com.softwarecleandevelopment.cryptowallet.useragreement.presentation.UserAgreementScreen

@Composable
fun CryptoWalletNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = AppScreens.LandingScreen.route,
    ) {

        composable(route = AppScreens.LandingScreen.route) {
            LandingScreen(
                onCreateWalletClicked = {
                    navController.navigate(AppScreens.UserAgreementScreen.route)
                }
            )
        }
        composable(route = AppScreens.UserAgreementScreen.route) {
            UserAgreementScreen(
                onContinueClicked = {
                    navController.navigate(AppScreens.RecoveryPhraseScreen.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = AppScreens.RecoveryPhraseScreen.route) {
            RecoveryPhraseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onContinueClicked = { mnemonic ->
                    navController.navigate(AppScreens.ConfirmPhraseScreen.route)
                }
            )
        }
        composable(route = AppScreens.ConfirmPhraseScreen.route) {
            ConfirmPhraseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}