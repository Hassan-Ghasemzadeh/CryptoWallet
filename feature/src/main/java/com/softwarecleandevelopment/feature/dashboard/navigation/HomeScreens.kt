package com.softwarecleandevelopment.feature.dashboard.navigation

sealed class HomeScreens(val route: String) {
    object SplashScreens : HomeScreens("splash_screen")
    object DashboardScreen : HomeScreens("dashboard_screen")
}