package com.softwarecleandevelopment.feature.wallet_home.presentation.receive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.receive.TokenItem
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive.ReceiveTokensViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveTokensScreen(
    viewModel: ReceiveTokensViewModel = hiltViewModel<ReceiveTokensViewModel>(),
    onItemClick: (address: String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val filteredTokens = viewModel.filteredTokens.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.navigateToAddress.collect { address ->
            onItemClick(address)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Receive") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Box
            OutlinedTextField(
                value = viewModel.searchQuery.collectAsState().value,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true
            )

            // Token List
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredTokens) { token ->
                    TokenItem(
                        token = token,
                        onItemClick = {
                            viewModel.onTokenSelected(token.id)
                        },
                        onCopyClick = {
                            viewModel.copyToClipBoard(token.id)
                        },
                    )
                }
            }
        }
    }
}
