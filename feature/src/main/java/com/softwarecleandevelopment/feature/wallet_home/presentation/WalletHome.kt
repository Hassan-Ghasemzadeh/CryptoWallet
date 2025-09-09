package com.softwarecleandevelopment.feature.wallet_home.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.core.common.utils.UiState
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.wallet_home.CoinRow
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.wallet_home.QuickAction
import com.softwarecleandevelopment.feature.R
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.wallet_home.WalletHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WalletHome(
    modifier: Modifier = Modifier,
    viewModel: WalletHomeViewModel = hiltViewModel(),
    onReceiveClick: () -> Unit = {},
    onSendClick: () -> Unit = {},
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()
    val uiState by viewModel.cryptos.collectAsState()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = viewModel::loadCryptoInfo,
        state = pullRefreshState,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "$0.00",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickAction(
                    icon = Icons.Outlined.KeyboardArrowUp,
                    label = stringResource(R.string.home_send_brn)
                ) { onSendClick() }
                QuickAction(
                    icon = Icons.Outlined.KeyboardArrowDown,
                    label = stringResource(R.string.home_receive_brn)
                ) { onReceiveClick() }
            }

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (uiState) {
                    is UiState.Error -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (uiState as UiState.Error).message,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    }

                    is UiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),

                                contentAlignment = Alignment.TopCenter
                            ) {
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    }

                    is UiState.Success<List<CryptoInfo>> -> {
                        items((uiState as UiState.Success<List<CryptoInfo>>).data) { coin ->
                            CoinRow(coin = coin, onClick = { /* open details */ })
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(4.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter
                    ) {
                        TextButton(onClick = { /* manage tokens */ }) {
                            Icon(
                                imageVector = Icons.Outlined.Settings, contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.home_managetoken_brn))
                        }
                    }
                }
            }
        }
    }
}