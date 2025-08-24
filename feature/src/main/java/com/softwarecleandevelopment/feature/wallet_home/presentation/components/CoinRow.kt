package com.softwarecleandevelopment.feature.wallet_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.feature.wallet_home.domain.models.Coin


@Composable
fun CoinRow(coin: Coin, onClick: () -> Unit) {
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