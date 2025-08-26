package com.softwarecleandevelopment.cryptowallet.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.softwarecleandevelopment.core.common.navigation.app_graph.AppGraph
import com.softwarecleandevelopment.core.common.navigation.screens.CreateWalletScreens
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.ConfirmPhraseScreen
import com.softwarecleandevelopment.cryptowallet.landing.presentation.LandingScreen
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.RecoveryPhraseScreen
import com.softwarecleandevelopment.cryptowallet.useragreement.presentation.UserAgreementScreen
import com.softwarecleandevelopment.feature.dashboard.navigation.HomeScreens

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
                    onContinueClicked = { derived ->

                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "derived",
                            derived
                        )
                        navController.navigate(CreateWalletScreens.ConfirmPhraseScreen.route)
                    }
                )
            }
            composable(route = CreateWalletScreens.ConfirmPhraseScreen.route) {
                val derived =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Derived>("derived")

                ConfirmPhraseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onSuccess = {
                        navController.navigate(
                            HomeScreens.DashboardScreen.route,
                        ) {
                            popUpTo(CreateWalletScreens.LandingScreen.route) {
                                inclusive = true
                            }
                        }
                    },
                    derived = derived,
                )
            }
        }
    }
}