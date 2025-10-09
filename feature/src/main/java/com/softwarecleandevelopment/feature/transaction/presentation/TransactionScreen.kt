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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.feature.R
import com.softwarecleandevelopment.feature.transaction.presentation.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel,
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val uiState by viewModel.transaction.collectAsState()
    val showInlineLoading = isRefreshing

    val transactionList = (uiState as? Resource.Success<List<Transaction>>)?.data
    val isEmptyList = transactionList?.isEmpty() == true


    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showInlineLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }
        // ---------------------------------------------

        //Display centered message when the list is empty ---
        if (isEmptyList && !isRefreshing) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_transactions_found),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (uiState) {
                    is Resource.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (uiState as Resource.Error).message,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        // Loading state is handled by LinearProgressIndicator at the top
                    }

                    is Resource.Success<List<Transaction>> -> {
                        // Display the items only if the list is NOT empty
                        if (transactionList != null && transactionList.isNotEmpty()) {
                            items(transactionList) { transaction ->
                                TransactionItem(transaction)
                            }
                        }
                    }
                }
            }
        }
    }
}