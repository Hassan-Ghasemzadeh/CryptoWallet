package com.softwarecleandevelopment.feature.wallet_home.presentation.components.wallet_home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.core.crypto.models.CoinInfo


@SuppressLint("DefaultLocale")
@Composable
fun CoinRow(coin: CoinInfo, onClick: () -> Unit) {
    ListItem(
        headlineContent = {
            Text(coin.name, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 18.sp)
        },
        supportingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "$${coin.priceUsd}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Spacer(Modifier.width(8.dp))
                val gain = coin.changePrecent >= 0
                val formattedString = String.format("%.2f", coin.changePrecent)

                Text(
                    text = (if (gain) "+" else "") + formattedString,
                    color = if (gain) Color(0xFF2ECC71) else Color(0xFFE74C3C),
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        leadingContent = {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                painter = painterResource(id = coin.iconRes),
                contentDescription = "crypto coin icon",
                contentScale = ContentScale.Crop
            )
        },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End) {
                Text(coin.symbol, style = MaterialTheme.typography.bodyMedium)
                Text(
                    "${coin.balance}",
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