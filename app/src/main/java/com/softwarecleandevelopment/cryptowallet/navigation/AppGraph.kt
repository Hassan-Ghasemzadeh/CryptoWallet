package com.softwarecleandevelopment.cryptowallet.navigation

sealed class AppGraph(val graph: String) {
    object CreateWalletGraph : AppGraph("create_wallet_graph")
}