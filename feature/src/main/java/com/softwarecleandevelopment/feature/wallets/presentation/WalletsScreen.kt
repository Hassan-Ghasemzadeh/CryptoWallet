package com.softwarecleandevelopment.feature.wallets.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallets.presentation.viewmodels.WalletsViewModel
import com.softwarecleandevelopment.feature.wallets.presentation.components.WalletItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun WalletsScreen(
    viewModel: WalletsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onCreateWallet: () -> Unit = {},
    onImportWallet: () -> Unit = {},
) {
    val wallets = viewModel.wallets.value
    LaunchedEffect(Unit) {
        viewModel.navigation.debounce(500).collect {
            onNavigateBack()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wallets", fontSize = 20.sp) }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Create Wallet
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCreateWallet() }
                    .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create",
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Create a New Wallet", fontSize = 16.sp)
            }

            HorizontalDivider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f))

            // Import Wallet
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onImportWallet() }
                    .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowDownward,
                    contentDescription = "Import",
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Import an Existing Wallet", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Wallet List
            LazyColumn {
                items(wallets) { wallet ->
                    WalletItem(
                        wallet = wallet,
                        onClick = {
                            viewModel.selectWallet(
                                wallet.id
                            )
                        })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
