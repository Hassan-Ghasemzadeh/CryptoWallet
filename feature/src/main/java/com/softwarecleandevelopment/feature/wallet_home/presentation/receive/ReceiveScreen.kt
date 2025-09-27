package com.softwarecleandevelopment.feature.wallet_home.presentation.receive


import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive.ReceiveCoinEvent
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive.ReceiveCoinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveCoinScreen(
    viewModel: ReceiveCoinViewModel = hiltViewModel(),
    address: String,
    onNavigateBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val state = viewModel.ui.collectAsState().value
    val navigateBack = viewModel.navigateBack.collectAsState().value
    LaunchedEffect(address) {
        viewModel.generateQrCode(address)
    }
    // one-shot toast
    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(navigateBack) {
        if (navigateBack) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(state.title) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(ReceiveCoinEvent.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(ReceiveCoinEvent.OnShareClick) }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            // QR card
            Box(
                Modifier
                    .padding(16.dp)
                    .heightIn(min = 280.dp),
                contentAlignment = Alignment.Center
            ) {
                state.qr?.let { QrImage(it) }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = state.walletName,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = state.address,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                FilledTonalButton(
                    onClick = { viewModel.onEvent(ReceiveCoinEvent.OnCopyClick) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Filled.ContentCopy, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Copy")
                }
                FilledTonalButton(
                    onClick = { viewModel.onEvent(ReceiveCoinEvent.OnShareClick) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Share")
                }
            }
        }
    }
}

@Composable
private fun QrImage(bitmap: Bitmap) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "ETH Address QR",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = RoundedCornerShape(50.dp)
                ambientShadowColor = Color.Black.copy(alpha = 0.2f)
                spotShadowColor = Color.Black.copy(alpha = 0.4f)
            }
            .clip(RoundedCornerShape(12.dp))
            .padding(4.dp)
    )
}