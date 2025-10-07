package com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.core.common.utils.formatToUSD
import java.util.Locale


@Composable
fun WalletInfo(
    price: String, change: Double,
    onDetailClicked: () -> Unit = { }
) {
    val gain = change >= 0
    val changePercent = String.format(Locale.getDefault(), "%.2f", change)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                enabled = true, onClick = {
                    onDetailClicked()
                })
            .fillMaxWidth(),
    ) {
        Text("Price:", fontSize = 16.sp)
        Row {
            Text(formatToUSD(price.toDouble(), 2), fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = (if (gain) "+" else "") + changePercent,
                color = if (gain) Color(0xFF2ECC71) else Color(0xFFE74C3C),
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                Icons.AutoMirrored.Filled.ArrowForwardIos,
                modifier = Modifier.size(20.dp),
                contentDescription = "Arrow forward"
            )
        }
    }
}
