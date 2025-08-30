package com.softwarecleandevelopment.feature.wallet_home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallet_home.domain.models.sampleCoins
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.CoinRow
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.QuickAction
import com.softwarecleandevelopment.feature.R
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.WalletHomeViewModel

@Composable
fun WalletHome(viewModel: WalletHomeViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
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
            QuickAction(
                icon = Icons.Outlined.KeyboardArrowUp,
                label = stringResource(R.string.home_send_brn)
            ) { /* TODO */ }
            QuickAction(
                icon = Icons.Outlined.KeyboardArrowDown,
                label = stringResource(R.string.home_receive_brn)
            ) { /* TODO */ }
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
                    Text(stringResource(R.string.home_managetoken_brn))
                }
            }
        }
    }
}