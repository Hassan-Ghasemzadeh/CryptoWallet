package com.softwarecleandevelopment.feature.dashboard.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.softwarecleandevelopment.feature.dashboard.domain.models.BottomNavigationItem
import com.softwarecleandevelopment.feature.setting.presentation.SettingsScreen
import com.softwarecleandevelopment.feature.transaction.presentation.TransactionsScreen
import com.softwarecleandevelopment.feature.wallet_home.presentation.WalletHome
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.WalletTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val items = listOf(
        BottomNavigationItem(
            "Wallet",
            Icons.Outlined.AccountBalanceWallet,
        ),
        BottomNavigationItem("Transactions", Icons.AutoMirrored.Outlined.ReceiptLong),
        BottomNavigationItem("Settings", Icons.Outlined.Settings)
    )
    var selected by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            if (selected == 0) WalletTopBar()
            else CenterAlignedTopAppBar(title = { Text(items[selected].label) })
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selected) {
            0 -> WalletHome(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            1 -> TransactionsScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            2 -> SettingsScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}