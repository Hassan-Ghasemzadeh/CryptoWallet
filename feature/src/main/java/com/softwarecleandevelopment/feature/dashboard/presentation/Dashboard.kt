package com.softwarecleandevelopment.feature.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val items = listOf(
        BottomItem(
            "Wallet",
            Icons.Outlined.AccountBalanceWallet,
        ),
        BottomItem("Transactions", Icons.AutoMirrored.Outlined.ReceiptLong),
        BottomItem("Settings", Icons.Outlined.Settings)
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
    ) { inner ->
        when (selected) {
            0 -> WalletHome(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
            )

            1 -> TransactionsScreen(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
            )

            2 -> SettingsScreen(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalletTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                )
                Spacer(Modifier.width(8.dp))
                Text("Wallet #1")
                Icon(
                    Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Right Arrow",
                )
            }
        },
    )
}

@Composable
private fun WalletHome(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(4.dp))
        Text(
            text = "$0.00",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickAction(icon = Icons.Outlined.KeyboardArrowUp, label = "Send") { /* TODO */ }
            QuickAction(icon = Icons.Outlined.KeyboardArrowDown, label = "Receive") { /* TODO */ }
            QuickAction(icon = Icons.Outlined.Add, label = "Buy") { /* TODO */ }
        }

        Spacer(Modifier.height(12.dp))
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(sampleCoins) { coin ->
                CoinRow(coin = coin, onClick = { /* open details */ })
            }
            item {
                Spacer(Modifier.height(4.dp))
                TextButton(onClick = { /* manage tokens */ }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Manage Tokens")
                }
            }
        }
    }
}

@Composable
private fun QuickAction(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FilledTonalIconButton(
            onClick = onClick,
            modifier = Modifier.size(64.dp)
        ) { Icon(icon, contentDescription = label, modifier = Modifier.size(28.dp)) }
        Spacer(Modifier.height(6.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun CoinRow(coin: Coin, onClick: () -> Unit) {
    ListItem(
        headlineContent = {
            Text(coin.name, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 18.sp)
        },
        supportingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "$${coin.price}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Spacer(Modifier.width(8.dp))
                val gain = coin.changePercent >= 0
                Text(
                    text = (if (gain) "+" else "") + "${coin.changePercent}%",
                    color = if (gain) Color(0xFF2ECC71) else Color(0xFFE74C3C),
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(coin.tint, MaterialTheme.shapes.small)
            )
        },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text(coin.symbolAmount, style = MaterialTheme.typography.bodyMedium)
                Text(
                    coin.symbol,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        overlineContent = null
    )
    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
}


@Composable
private fun TransactionsScreen(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Text("Transactions will appear here")
    }
}

@Composable
private fun SettingsScreen(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Text("Settings")
    }
}


private data class BottomItem(val label: String, val icon: ImageVector)
data class Coin(
    val name: String,
    val symbol: String,
    val price: String,
    val changePercent: Double,
    val symbolAmount: String,
    val tint: Color
)

private val sampleCoins = listOf(
    Coin("Bitcoin", "BTC", "115,777.00", 2.24, "0 BTC", Color(0xFFF7931A)),
    Coin("Ethereum", "ETH", "4,713.59", 8.53, "0 ETH", Color(0xFF3C3C3D)),
    Coin("BNB Chain", "BNB", "888.54", 4.06, "0 BNB", Color(0xFFF3BA2F)),
    Coin("Solana", "SOL", "204.65", 10.76, "0 SOL", Color(0xFF14F195))
)