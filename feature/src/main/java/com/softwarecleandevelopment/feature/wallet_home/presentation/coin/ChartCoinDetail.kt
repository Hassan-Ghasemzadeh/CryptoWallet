package com.softwarecleandevelopment.feature.wallet_home.presentation.coin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ChartDetailParams
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin.Sparkline
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.coin.ChartCoinDetailViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartCoinDetailScreen(
    params: ChartDetailParams?,
    viewModel: ChartCoinDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // Data Collection
    val coin by viewModel.coin.collectAsState()
    val livePrice by viewModel.livePrice.collectAsState()

    // State for Time Period Tabs
    var selectedTab by remember { mutableStateOf("24H") }
    val tabs = listOf("24H")

    LaunchedEffect(Unit) {
        viewModel.load(
            params?.coinId ?: "", params?.asset ?: ""
        )
    }
    // Main Scaffold structure
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(coin?.name ?: "Loading...", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
            )
        }) { padding ->

        // 4. Loading/Content Switching
        coin?.let { c ->
            // Use verticalScroll to make the entire content scrollable
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()) // MAKE IT SCROLLABLE
                    .padding(horizontal = 16.dp) // Apply horizontal padding once
            ) {

                // Price and Change Row
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Current Price
                    Text(
                        text = "$${String.format(Locale.US, "%,.2f", livePrice ?: c.priceUsd)}",
                        fontSize = 32.sp, // Slightly larger size for emphasis
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(12.dp))

                    // 24H Change Percentage
                    val change24h = c.changePercent24Hr ?: 0.0
                    val changeColor = if (change24h >= 0) Color(0xFF2EB86D) else Color.Red
                    val changeText = if (change24h >= 0) {
                        "+${String.format(Locale.US, "%.2f", change24h)}%"
                    } else {
                        "${String.format(Locale.US, "%.2f", change24h)}%"
                    }

                    Text(
                        text = changeText,
                        color = changeColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Chart/Sparkline
                Spacer(Modifier.height(16.dp))
                // Give Sparkline a specific height to ensure it appears
                Sparkline(
                    points = c.sparkline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp) // Recommended to define chart height
                )

                // Time Period Tabs (Improved with LazyRow and Tab/Chip styling)
                Spacer(Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) {
                    items(tabs.size) { index ->
                        val t = tabs[index]
                        val isSelected = t == selectedTab

                        // Use a Composable like FilterChip or just a styled Surface/Card
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable {
                                selectedTab = t

                            }) {
                            Text(
                                text = t,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Market Data Section
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                InfoRow(
                    label = "Market Cap",
                    value = c.marketCapUsd?.let { "$${formatLarge(it)}" } ?: "-")
                InfoRow(label = "Circulating Supply", value = c.circulatingSupply?.let {
                    String.format(
                        Locale.US, "%,.2f", it
                    ) + " ${c.symbol.uppercase(Locale.ROOT)}"
                } ?: "-")
                InfoRow(label = "Total Supply", value = c.totalSupply?.let {
                    String.format(
                        Locale.US, "%,.2f", it
                    ) + " ${c.symbol.uppercase(Locale.ROOT)}"
                } ?: "-")

                // Links Section
                Spacer(Modifier.height(24.dp))
                Text(
                    "LINKS",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Column {
                    c.links.forEach { link ->
                        // The individual link item wrapped in a clickable Surface for better UX/UI
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                // Assuming you want the whole row clickable:
                                .clickable { /* launch URL logic here */ }) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp) // Add padding to the item itself
                            ) {
                                Text(
                                    link.title,
                                    style = MaterialTheme.typography.bodyMedium, // Changed to bodyMedium for better title readability
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    link.url,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant // Use onSurfaceVariant for secondary info (URL)
                                )
                            }
                        }
                        HorizontalDivider(
                            Modifier, DividerDefaults.Thickness, DividerDefaults.color
                        )
                        // Separator after each link item
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

// Helper Composable for information rows
@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.SemiBold)
    }
    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
    // Add a divider after each row for a clean list look
}

// Helper function for formatting large numbers
private fun formatLarge(v: Double): String {
    val abs = kotlin.math.abs(v)
    return when {
        abs >= 1_000_000_000_000 -> String.format(Locale.US, "%,.2fT", v / 1_000_000_000_000.0)
        abs >= 1_000_000_000 -> String.format(Locale.US, "%,.2fB", v / 1_000_000_000.0)
        abs >= 1_000_000 -> String.format(Locale.US, "%,.2fM", v / 1_000_000.0)
        abs >= 1_000 -> String.format(Locale.US, "%,.2fK", v / 1_000.0)
        else -> String.format(Locale.US, "%,.2f", v)
    }
}