package com.softwarecleandevelopment.feature.wallet_home.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.send.SendCoinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendCoinScreen(
    viewModel: SendCoinViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Send Token") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // Address input
            OutlinedTextField(
                value = state.address,
                onValueChange = viewModel::onAddressChanged,
                label = { Text("Address or Domain Name") },
                placeholder = { Text("Search or Enter") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    Row {
                        TextButton(onClick = { /* paste action */ }) {
                            Text("Paste")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = { /* QR Scan */ }) {
                            Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan QR")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Amount input
            OutlinedTextField(
                value = state.amount,
                onValueChange = viewModel::onAmountChanged,
                label = { Text("Amount") },
                placeholder = { Text("FEG Amount") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                trailingIcon = {
                    TextButton(onClick = { viewModel.onMaxClicked() }) {
                        Text("Max")
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Next button
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = state.address.isNotBlank() && state.amount.isNotBlank()
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSendScreen() {
    SendCoinScreen()
}