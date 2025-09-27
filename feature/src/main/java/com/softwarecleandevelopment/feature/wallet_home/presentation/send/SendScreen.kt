package com.softwarecleandevelopment.feature.wallet_home.presentation.send


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.softwarecleandevelopment.feature.wallet_home.presentation.components.send.CameraPreview
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.scanner.ScannerViewModel
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.send.SendCoinViewModel
import com.softwarecleandevelopment.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendCoinScreen(
    sendCoinViewModel: SendCoinViewModel = hiltViewModel<SendCoinViewModel>(),
    scannerViewModel: ScannerViewModel = hiltViewModel<ScannerViewModel>(),
    balance: Double = 0.0,
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    val state by sendCoinViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showScanner by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }

    // permission launcher
    val cameraPermissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> cameraPermissionState.value = granted }

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.value) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }
    if (showScanner && cameraPermissionState.value) {
        CameraPreview(
            scannerViewModel, onScanned = {
                sendCoinViewModel.onAddressChanged(it)
                showScanner = false
            })
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.send_token_title, "Eth")) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    })
            }) { padding ->
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
                    onValueChange = sendCoinViewModel::onAddressChanged,
                    label = { Text(stringResource(R.string.send_token_address_label)) },
                    placeholder = { Text(stringResource(R.string.send_token_address_placeholder)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                        Row {
                            TextButton(onClick = {
                                val copiedText = sendCoinViewModel.getCopiedText()
                                if (copiedText != null) {
                                    sendCoinViewModel.onAddressChanged(copiedText)
                                }
                            }) {
                                Text(stringResource(R.string.send_token_paste))
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(onClick = {
                                if (!cameraPermissionState.value) {
                                    launcher.launch(Manifest.permission.CAMERA)
                                } else {
                                    showScanner = true
                                }
                            }) {
                                Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan QR")
                            }
                        }
                    })

                Spacer(modifier = Modifier.height(24.dp))

                // Amount input
                OutlinedTextField(
                    value = state.amount,
                    onValueChange = sendCoinViewModel::onAmountChanged,
                    label = { Text(stringResource(R.string.send_token_amount_title)) },
                    placeholder = { Text(stringResource(R.string.send_token_amount, "Eth")) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    trailingIcon = {
                        TextButton(onClick = { sendCoinViewModel.onMaxClicked(balance) }) {
                            Text(stringResource(R.string.send_token_max))
                        }
                    })

                Spacer(modifier = Modifier.weight(1f))

                // Next button
                Button(
                    onClick = {
                        showSheet = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = state.address.isNotBlank() && state.amount.isNotBlank()
                ) {
                    Text(stringResource(R.string.send_token_next))
                }
                if (showSheet) {
                    ConfirmSendBottomSheet(
                        toAddress = state.address,
                        tokenAmount = state.amount
                    ) {
                        showSheet = false
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSendScreen() {
    SendCoinScreen(balance = 0.0)
}