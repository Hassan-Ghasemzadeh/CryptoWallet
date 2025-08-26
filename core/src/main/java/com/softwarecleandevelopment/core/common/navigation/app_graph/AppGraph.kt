package com.softwarecleandevelopment.core.common.navigation.app_graph

sealed class AppGraph(val graph: String) {
    object CreateWalletGraph : AppGraph("create_wallet_graph")
    object DashboardGraph : AppGraph("dashboard_graph")
}