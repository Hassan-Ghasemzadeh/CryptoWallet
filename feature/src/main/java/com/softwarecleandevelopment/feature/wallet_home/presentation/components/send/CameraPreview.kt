package com.softwarecleandevelopment.feature.wallet_home.presentation.components.send

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.scanner.ScannerViewModel

@Composable
fun CameraPreview(
    scannerViewModel: ScannerViewModel,
    onScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    DisposableEffect(Unit) {
        scannerViewModel.bindCamera(lifecycleOwner, context, previewView, onScanned)
        onDispose {
            scannerViewModel.stopCamera()
        }
    }

    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
}