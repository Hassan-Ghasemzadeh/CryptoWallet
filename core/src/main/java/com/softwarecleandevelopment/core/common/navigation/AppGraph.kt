package com.softwarecleandevelopment.core.common.navigation

sealed class AppGraph(val graph: String) {
    object CreateWalletGraph : AppGraph("create_wallet_graph")
}