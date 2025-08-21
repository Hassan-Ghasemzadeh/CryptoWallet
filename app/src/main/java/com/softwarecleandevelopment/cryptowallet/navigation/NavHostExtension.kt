package com.softwarecleandevelopment.cryptowallet.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.softwarecleandevelopment.core.common.navigation.AppGraph
import com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.ConfirmPhraseScreen
import com.softwarecleandevelopment.cryptowallet.landing.LandingScreen
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.RecoveryPhraseScreen
import com.softwarecleandevelopment.cryptowallet.useragreement.presentation.UserAgreementScreen

object NavHostExtension {
    fun NavGraphBuilder.createWalletGraph(navController: NavController) {
        navigation(
            startDestination = CreateWalletScreens.LandingScreen.route,
            route = AppGraph.CreateWalletGraph.graph
        ) {

            composable(route = CreateWalletScreens.LandingScreen.route) {
                LandingScreen(
                    onCreateWalletClicked = {
                        navController.navigate(CreateWalletScreens.UserAgreementScreen.route)
                    }
                )
            }
            composable(route = CreateWalletScreens.UserAgreementScreen.route) {
                UserAgreementScreen(
                    onContinueClicked = {
                        navController.navigate(CreateWalletScreens.RecoveryPhraseScreen.route)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = CreateWalletScreens.RecoveryPhraseScreen.route) {
                RecoveryPhraseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onContinueClicked = { mnemonic ->
                        navController.navigate(CreateWalletScreens.ConfirmPhraseScreen.route)
                    }
                )
            }
            composable(route = CreateWalletScreens.ConfirmPhraseScreen.route) {
                ConfirmPhraseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}