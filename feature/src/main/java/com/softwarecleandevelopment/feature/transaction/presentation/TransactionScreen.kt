package com.softwarecleandevelopment.feature.transaction.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.softwarecleandevelopment.feature.transaction.presentation.components.TransactionItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.feature.R
import com.softwarecleandevelopment.feature.transaction.presentation.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    modifier: Modifier,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val transactions = viewModel.transaction.collectAsState().value
    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val refreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = viewModel::fetchTransactions,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            when (transactions) {
                is Resource.Error -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = transactions.message,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                        )
                    }
                }

                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        //skipping empty for now
                    }
                }

                is Resource.Success<List<Transaction>> -> {
                    //Transactions List (LazyColumn for performance)
                    if (transactions.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.no_transactions_found))
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(transactions.data) { transaction ->
                                // The composable for a single item
                                TransactionItem(transaction = transaction)
                            }
                        }
                    }
                }
            }
        }
    }
}