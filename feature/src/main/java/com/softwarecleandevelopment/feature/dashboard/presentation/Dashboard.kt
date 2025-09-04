package com.softwarecleandevelopment.feature.dashboard.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.dashboard.domain.models.BottomNavigation
import com.softwarecleandevelopment.feature.dashboard.domain.models.items
import com.softwarecleandevelopment.feature.dashboard.presentation.viewmodels.BottomNavigationViewModel
import com.softwarecleandevelopment.feature.setting.presentation.SettingsScreen
import com.softwarecleandevelopment.feature.transaction.presentation.TransactionsScreen
import com.softwarecleandevelopment.feature.wallet_home.presentation.WalletHome
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.wallet_home.WalletTopBar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onTitleClicked: () -> Unit = {},
    onReceiveClick: () -> Unit = {},
    onSendClick: () -> Unit = { }
) {
    val viewModel: BottomNavigationViewModel = hiltViewModel()
    val selectedIndex = viewModel.selectedIndex.value

    Scaffold(topBar = {
        if (selectedIndex == BottomNavigation.HOME.index) WalletTopBar({
            onTitleClicked()
        })
        else CenterAlignedTopAppBar(
            title = { Text(items[selectedIndex].label) })
    }, bottomBar = {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = viewModel.isSelected(index),
                    onClick = { viewModel.navigate(index) },
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) })
            }
        }
    }) { innerPadding ->
        when (selectedIndex) {
            BottomNavigation.HOME.ordinal -> WalletHome(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onReceiveClick = onReceiveClick,
                onSendClick = onSendClick
            )

            BottomNavigation.TRANSACTION.ordinal -> TransactionsScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            BottomNavigation.SETTING.ordinal -> SettingsScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}