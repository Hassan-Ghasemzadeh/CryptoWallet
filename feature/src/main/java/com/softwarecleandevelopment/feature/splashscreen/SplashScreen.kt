package com.softwarecleandevelopment.feature.splashscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softwarecleandevelopment.core.common.navigation.AppGraph

@Composable
fun SplashScreen(
    navController: NavController,
    handleWalletNavigationViewModel: HandleWalletNavigationViewModel = hiltViewModel()
) {
    val isWalletCreated by handleWalletNavigationViewModel.isWalletCreated()
        .collectAsState(initial = false)

    // LaunchedEffect is perfect here. It runs once when the key changes.
    LaunchedEffect(isWalletCreated) {
        val destination = if (isWalletCreated) {
            AppGraph.DashboardGraph.graph
        } else {
            AppGraph.CreateWalletGraph.graph
        }
        navController.navigate(destination) {
            // Pop the back stack to remove the splash screen
            popUpTo("splash_screen_route") {
                inclusive = true
            }
        }
    }

    // Your splash screen UI (e.g., logo, loading indicator)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}