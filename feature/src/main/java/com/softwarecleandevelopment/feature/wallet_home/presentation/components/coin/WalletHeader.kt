package com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.core.crypto.models.CoinInfo


@Composable
fun WalletHeader(coin: CoinInfo?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(
                coin?.iconRes ?: com.softwarecleandevelopment.feature.R.drawable.app_icon,
            ),
            contentDescription = "coin Logo",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop

        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = if (coin == null) {
                ""
            } else {
                "${coin.balance} ${coin.symbol}"
            },
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (coin == null) {
                ""
            } else {
                "$${(coin.priceUsd).times(coin.balance)}"
            },
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}