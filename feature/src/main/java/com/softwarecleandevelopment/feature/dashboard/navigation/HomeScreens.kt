package com.softwarecleandevelopment.feature.dashboard.navigation

sealed class HomeScreens(val route: String) {

    data object SplashScreens : HomeScreens("splash_screen")
    data object DashboardScreen : HomeScreens("dashboard_screen")
    data object WalletsScreen : HomeScreens("wallets_screen")
    data object WalletDetailScreen : HomeScreens("wallet_detail_screen")
    data object WalletSecretPhraseScreen : HomeScreens("wallet_Secret_phrase_screen")
    data object ReceiveScreen : HomeScreens("receive_screen")
    data object ReceiveTokensScreen : HomeScreens("receive_tokens_screen")
    data object SendScreen : HomeScreens("Send_screen")
    data object SendTokensScreen : HomeScreens("send_tokens_screen")
    data object CoinScreen : HomeScreens("coin_screen")
    data object CoinDetailScreen : HomeScreens("coin_detail_screen")

}