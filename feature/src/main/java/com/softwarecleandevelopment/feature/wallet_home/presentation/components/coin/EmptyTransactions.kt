package com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.feature.R


@Composable
fun EmptyTransactions() {
    Spacer(Modifier.height(24.dp))
    Text(
        text = stringResource(R.string.empty_transaction),
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}