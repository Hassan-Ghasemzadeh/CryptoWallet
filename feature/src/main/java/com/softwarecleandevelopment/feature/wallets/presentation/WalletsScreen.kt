package com.softwarecleandevelopment.feature.wallets.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallets.presentation.viewmodels.WalletsViewModel
import com.softwarecleandevelopment.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletsScreen(
    viewModel: WalletsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onCreateWallet: () -> Unit = {},
    onImportWallet: () -> Unit = {},
) {
    val wallets = viewModel.wallets.value
    LaunchedEffect(Unit) {
        viewModel.navigation.collect {
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

@Composable
fun WalletItem(wallet: WalletEntity, onClick: () -> Unit) {
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
            Icons.Default.Settings,
            contentDescription = "Settings",
            tint = Color.Gray
        )
    }
}