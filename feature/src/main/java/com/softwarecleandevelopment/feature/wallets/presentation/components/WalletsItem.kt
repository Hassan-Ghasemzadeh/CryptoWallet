package com.softwarecleandevelopment.feature.wallets.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.R
import com.softwarecleandevelopment.feature.wallets.domain.models.UpdateWalletEvent


@Composable
fun WalletItem(
    wallet: WalletEntity,
    onClick: () -> Unit,
    onSettingClick: (UpdateWalletEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        // Wallet Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "Wallet Icon",
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Wallet Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(wallet.name, fontWeight = FontWeight.Bold)
            Text("Multi-Coin", fontSize = 12.sp)
        }

        // Selected Mark
        if (wallet.isActive) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Selected",
                tint = Color.Blue
            )
        }

        // Settings Icon
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier.clickable(
                enabled = true,
                onClick = {
                    onSettingClick(
                        UpdateWalletEvent(
                            wallet.name,
                            wallet.id,
                            mnemonic = wallet.mnemonic,
                        )
                    )
                },
            ),
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = Color.Gray
        )
    }
}