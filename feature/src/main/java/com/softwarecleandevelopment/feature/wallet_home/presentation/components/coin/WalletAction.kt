package com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WalletActions(
    onSendClicked: () -> Unit = {},
    onReceiveClicked: () -> Unit = {},
    onSwapClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton("Send", Icons.Filled.ArrowUpward, onClicked = onSendClicked)
        ActionButton("Receive", Icons.Filled.ArrowDownward, onClicked = onReceiveClicked)
        ActionButton("Swap", Icons.Filled.SwapVert, onClicked = onSwapClicked)
    }
}

@Composable
private fun ActionButton(label: String, icon: ImageVector, onClicked: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(
            true,
            onClick = onClicked,
        )
    ) {
        Surface(
            modifier = Modifier.size(56.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = CircleShape
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(label, fontSize = 14.sp)
    }
}
