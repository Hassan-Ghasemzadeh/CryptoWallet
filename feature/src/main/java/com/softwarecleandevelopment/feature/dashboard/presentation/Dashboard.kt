package com.softwarecleandevelopment.feature.dashboard.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.feature.dashboard.domain.models.BottomNavigation
import com.softwarecleandevelopment.feature.dashboard.domain.models.items
import com.softwarecleandevelopment.feature.dashboard.presentation.viewmodels.BottomNavigationViewModel
import com.softwarecleandevelopment.feature.setting.presentation.SettingsScreen
import com.softwarecleandevelopment.feature.transaction.presentation.TransactionsScreen
import com.softwarecleandevelopment.feature.transaction.presentation.viewmodel.TransactionViewModel
import com.softwarecleandevelopment.feature.wallet_home.presentation.home.WalletHome
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.wallet_home.WalletTopBar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onTitleClicked: () -> Unit = {},
    onReceiveClick: () -> Unit = {},
    onSendClick: () -> Unit = { },
    onItemClick: (coin: CoinInfo) -> Unit = {},
    transactionViewModel: TransactionViewModel = hiltViewModel(),
) {
    val viewModel: BottomNavigationViewModel = hiltViewModel()
    val selectedIndex = viewModel.selectedIndex.value

    Scaffold(
        topBar = {
            if (selectedIndex == BottomNavigation.HOME.index) WalletTopBar({
                onTitleClicked()
            })
            else CenterAlignedTopAppBar(
                title = { Text(items[selectedIndex].label) },
                actions = {
                    if (selectedIndex == BottomNavigation.TRANSACTION.index)
                        IconButton(onClick = transactionViewModel::fetchTransactions) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Refresh Data")
                        }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = viewModel.isSelected(index),
                        onClick = { viewModel.navigate(index) },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) })
                }
            }
        },
    ) { innerPadding ->
        when (selectedIndex) {
            BottomNavigation.HOME.ordinal -> WalletHome(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onReceiveClick = onReceiveClick,
                onSendClick = onSendClick,
                onItemClick = onItemClick,
            )

            BottomNavigation.TRANSACTION.ordinal -> TransactionsScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                viewModel = transactionViewModel
            )

            BottomNavigation.SETTING.ordinal -> SettingsScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}