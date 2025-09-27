package com.softwarecleandevelopment.feature.dashboard.navigation

sealed class HomeScreens(val route: String) {
    object SplashScreens : HomeScreens("splash_screen")
    object DashboardScreen : HomeScreens("dashboard_screen")
    object WalletsScreen : HomeScreens("wallets_screen")
    object WalletDetailScreen : HomeScreens("wallet_detail_screen")
    object WalletSecretPhraseScreen : HomeScreens("wallet_Secret_phrase_screen")
    object ReceiveScreen : HomeScreens("receive_screen")
    object ReceiveTokensScreen : HomeScreens("receive_tokens_screen")
    object SendScreen : HomeScreens("Send_screen")
}