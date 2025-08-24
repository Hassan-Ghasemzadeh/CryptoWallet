package com.softwarecleandevelopment.feature.dashboard.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(val label: String, val icon: ImageVector)

val items = listOf(
    BottomNavigationItem(
        "Wallet",
        Icons.Outlined.AccountBalanceWallet,
    ),
    BottomNavigationItem(
        "Transactions",
        Icons.AutoMirrored.Outlined.ReceiptLong
    ),
    BottomNavigationItem(
        "Settings",
        Icons.Outlined.Settings
    )
)
