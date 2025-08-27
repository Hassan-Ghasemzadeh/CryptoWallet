package com.softwarecleandevelopment.feature.dashboard.navigation

sealed class HomeScreens(val route: String) {
    object SplashScreens : HomeScreens("splash_screen")
    object DashboardScreen : HomeScreens("dashboard_screen")
    object WalletsScreen : HomeScreens("wallets_screen")
    object WalletDetailScreen : HomeScreens("wallet_detail_screen")
    object WalletSecretPhraseScreen : HomeScreens("wallet_Secret_phrase_screen")
}