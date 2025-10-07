package com.softwarecleandevelopment.feature.wallet_home.presentation.coin


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.coin.CoinsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartCoinsListScreen(
    viewModel: CoinsListViewModel = hiltViewModel<CoinsListViewModel>(),
    openDetail: (String, String) -> Unit
) {
    val items by viewModel.items.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Portfolio") })
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { openDetail(item.id, item.asset) }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(item.name, fontWeight = FontWeight.Medium)
                        Text(
                            item.symbol.uppercase(Locale.ROOT),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    // Column for Price and Change (Aligned to the End)
                    Column(horizontalAlignment = Alignment.End) {
                        val price = item.livePrice ?: item.staticPrice

                        // 1. Price Text
                        Text(
                            text = "$${String.format(Locale.US, "%,.2f", price)}",
                            fontWeight = FontWeight.Bold,
                        )

                        // 2. Change Percentage Text
                        val change24h = item.change24h ?: 0.0
                        val changeText = if (change24h >= 0) {
                            "+${String.format(Locale.US, "%.2f", change24h)}%"
                        } else {
                            "${String.format(Locale.US, "%.2f", change24h)}%"
                        }
                        val changeColor = if (change24h >= 0) Color(0xFF2EB86D) else Color.Red

                        Text(
                            text = changeText,
                            color = changeColor,
                            style = MaterialTheme.typography.bodySmall // Use a smaller style for the change value
                        )
                    }
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChartCoin() {
    ChartCoinsListScreen() { id, name -> }
}