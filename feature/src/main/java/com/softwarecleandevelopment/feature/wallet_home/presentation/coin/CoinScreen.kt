@file:OptIn(ExperimentalMaterial3Api::class)

package com.softwarecleandevelopment.feature.wallet_home.presentation.coin


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ReceiveNavigationParams
import com.softwarecleandevelopment.feature.wallet_home.domain.models.SendNavigationParams
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin.EmptyTransactions
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin.TransactionItem
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin.WalletActions
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin.WalletHeader
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin.WalletInfo
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin.WalletTopBar
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.coin.CoinViewModel
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.coin.CoinNavigation

@Composable
fun CoinScreen(
    viewModel: CoinViewModel = hiltViewModel(),
    coin: CoinInfo?,
    onBackClicked: () -> Unit = {},
    onSendClicked: (SendNavigationParams) -> Unit = {},
    onReceiveClicked: (ReceiveNavigationParams) -> Unit = {},
) {
    val context = LocalContext.current
    val transactions = viewModel.transactions.collectAsState().value
    val address = viewModel.address.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.fetchTransactions(
            coin,
        )
        viewModel.navigationEvent.collect { params ->
            when (params) {
                is CoinNavigation.NavigateToReceive -> {
                    onReceiveClicked(params.data)
                }

                is CoinNavigation.NavigateToSend -> {
                    onSendClicked(params.data)
                }

                CoinNavigation.OnSwapClicked -> {
                    Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    Scaffold(
        topBar = {
            WalletTopBar(
                coin?.symbol ?: "", onBackClicked = { onBackClicked() }
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            WalletHeader(coin)
            Spacer(Modifier.height(32.dp))
            WalletActions(
                onSendClicked = {
                    viewModel.onSendClicked(coin)
                },
                onReceiveClicked = {
                    viewModel.onReceiveClicked(coin)
                },
                onSwapClicked = {
                    viewModel.onSwapClicked()
                }
            )
            Spacer(Modifier.height(32.dp))
            WalletInfo(
                price = (coin?.priceUsd ?: 0.0).toString(), change = coin?.changePrecent ?: 0.0
            )
            Spacer(Modifier.height(32.dp))
            HorizontalDivider(
                Modifier.fillMaxWidth(), DividerDefaults.Thickness, DividerDefaults.color
            )
            if (transactions != null && transactions.isNotEmpty()) {
                LazyColumn {
                    items(transactions.size) {
                        TransactionItem(
                            transaction = transactions[it],
                            coinSymbol = coin?.symbol ?: "",
                            address = address,
                            modifier = Modifier,
                        )
                    }
                }
            } else {
                EmptyTransactions()
            }
        }
    }
}
