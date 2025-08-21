package com.softwarecleandevelopment.cryptowallet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.softwarecleandevelopment.cryptowallet.navigation.NavHostExtension.createWalletGraph

@Composable
fun CryptoWalletNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = AppGraph.CreateWalletGraph.graph,
    ) {
        createWalletGraph(navController = navController)
    }
}