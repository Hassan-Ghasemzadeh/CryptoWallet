package com.softwarecleandevelopment.cryptowallet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softwarecleandevelopment.cryptowallet.navigation.NavHostExtension.createWalletGraph
import com.softwarecleandevelopment.feature.dashboard.navigation.DashboardNavHostExtension.createDashboardGraph
import com.softwarecleandevelopment.feature.dashboard.navigation.HomeScreens
import com.softwarecleandevelopment.feature.splash_screen.presentation.SplashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = HomeScreens.SplashScreens.route,
    ) {

        composable(
            route = HomeScreens.SplashScreens.route,
        ) {
            SplashScreen(
                navController,
            )
        }
        createWalletGraph(navController = navController)
        createDashboardGraph(navController = navController)


    }
}