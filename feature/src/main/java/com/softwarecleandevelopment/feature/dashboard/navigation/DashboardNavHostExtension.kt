package com.softwarecleandevelopment.feature.dashboard.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.softwarecleandevelopment.core.common.navigation.app_graph.AppGraph
import com.softwarecleandevelopment.core.common.navigation.screens.CreateWalletScreens
import com.softwarecleandevelopment.feature.dashboard.presentation.DashboardScreen
import com.softwarecleandevelopment.feature.wallet_home.presentation.ReceiveCoinScreen
import com.softwarecleandevelopment.feature.wallet_home.presentation.SendCoinScreen
import com.softwarecleandevelopment.feature.wallets.domain.models.UpdateWalletEvent
import com.softwarecleandevelopment.feature.wallets.presentation.SecretPhraseScreen
import com.softwarecleandevelopment.feature.wallets.presentation.WalletDetailScreen
import com.softwarecleandevelopment.feature.wallets.presentation.WalletsScreen


object DashboardNavHostExtension {
    @RequiresApi(Build.VERSION_CODES.O)
    fun NavGraphBuilder.createDashboardGraph(navController: NavController) {
        navigation(
            startDestination = HomeScreens.DashboardScreen.route,
            route = AppGraph.DashboardGraph.graph
        ) {
            composable(
                route = HomeScreens.DashboardScreen.route,
            ) {
                DashboardScreen(
                    {
                        navController.navigate(
                            HomeScreens.WalletsScreen.route
                        )
                    },
                    onReceiveClick = {
                        navController.navigate(
                            HomeScreens.ReceiveScreen.route
                        )
                    },
                    onSendClick = {
                        navController.navigate(
                            HomeScreens.SendScreen.route,
                        )
                    }
                )
            }
            composable(
                route = HomeScreens.WalletsScreen.route,
            ) {
                WalletsScreen(
                    navController,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onCreateWallet = {
                        navController.navigate(CreateWalletScreens.UserAgreementScreen.route)
                    },
                    onSettingClicked = { event ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "updateWalletEvent",
                            event,
                        )
                        navController.navigate(
                            HomeScreens.WalletDetailScreen.route,
                        )
                    },
                    onImportWallet = {

                    },
                )
            }
            composable(
                route = HomeScreens.WalletDetailScreen.route,
            ) {
                val event =
                    navController.previousBackStackEntry?.savedStateHandle?.get<UpdateWalletEvent>("updateWalletEvent")

                WalletDetailScreen(event = event, onNavigateBack = {
                    navController.popBackStack()
                }, onNavigateToCreateWallet = {
                    navController.navigate(CreateWalletScreens.LandingScreen.route) {
                        popUpTo(CreateWalletScreens.LandingScreen.route) {
                            inclusive = true
                        }
                    }
                }, onShowScreenPhrase = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "secretPhraseEvent",
                        event?.mnemonic,
                    )
                    navController.navigate(HomeScreens.WalletSecretPhraseScreen.route)
                })
            }
            composable(
                route = HomeScreens.WalletSecretPhraseScreen.route,
            ) {
                val mnemonic =
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("secretPhraseEvent")
                SecretPhraseScreen(
                    mnemonic = mnemonic, onNavigateBack = {
                        navController.popBackStack()
                    })
            }
            composable(
                route = HomeScreens.ReceiveScreen.route,
            ) {
                ReceiveCoinScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    })
            }
            composable(
                route = HomeScreens.SendScreen.route,
            ) {
                SendCoinScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}