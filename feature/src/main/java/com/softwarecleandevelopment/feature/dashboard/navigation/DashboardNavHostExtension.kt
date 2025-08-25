package com.softwarecleandevelopment.feature.dashboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.softwarecleandevelopment.core.common.navigation.AppGraph
import com.softwarecleandevelopment.feature.dashboard.presentation.DashboardScreen
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
                        navController.popBackStack()
                    },
                    onCreateWallet = {
                        navController.navigate("user_agreement_screen")
                    },
                    onImportWallet = {

                    }
                )
            }
        }
    }
}