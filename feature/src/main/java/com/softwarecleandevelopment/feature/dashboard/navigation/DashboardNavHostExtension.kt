package com.softwarecleandevelopment.feature.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.softwarecleandevelopment.core.common.navigation.app_graph.AppGraph
import com.softwarecleandevelopment.core.common.navigation.screens.CreateWalletScreens
import com.softwarecleandevelopment.feature.dashboard.presentation.DashboardScreen
import com.softwarecleandevelopment.feature.wallets.domain.models.UpdateWalletEvent
import com.softwarecleandevelopment.feature.wallets.presentation.WalletDetailScreen
import com.softwarecleandevelopment.feature.wallets.presentation.WalletsScreen


object DashboardNavHostExtension {
    fun NavGraphBuilder.createDashboardGraph(navController: NavController) {
        navigation(
            startDestination = HomeScreens.DashboardScreen.route,
            route = AppGraph.DashboardGraph.graph
        ) {
            composable(
                route = HomeScreens.DashboardScreen.route,
            ) {
                DashboardScreen({
                    navController.navigate(
                        HomeScreens.WalletsScreen.route
                    )
                })
            }
            composable(
                route = HomeScreens.WalletsScreen.route,
            ) {
                WalletsScreen(
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
                WalletDetailScreen(
                    event = event,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}