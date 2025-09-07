package com.softwarecleandevelopment.feature.wallet_home.presentation.components.send

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val frameSize = size.width * 0.8f
                    val frameLeft = (size.width - frameSize) / 2
                    val frameTop = (size.height - frameSize) / 2
                    val frameRight = frameLeft + frameSize
                    val frameBottom = frameTop + frameSize
                    val cornerLength = 30.dp.toPx()
                    val cornerStrokeWidth = 4.dp.toPx()
                    val cornerColor = Color.White

                    drawRect(Color.Black.copy(alpha = 0.5f))

                    drawIntoCanvas { canvas ->
                        canvas.drawRect(
                            left = frameLeft,
                            top = frameTop,
                            right = frameRight,
                            bottom = frameBottom,
                            paint = Paint().apply {
                                color = Color.Black
                                blendMode = BlendMode.Clear
                            }
                        )
                    }

                    // top left
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameLeft, frameTop + cornerLength),
                        end = Offset(frameLeft, frameTop),
                        strokeWidth = cornerStrokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameLeft, frameTop),
                        end = Offset(frameLeft + cornerLength, frameTop),
                        strokeWidth = cornerStrokeWidth
                    )

                    // top right
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameRight, frameTop + cornerLength),
                        end = Offset(frameRight, frameTop),
                        strokeWidth = cornerStrokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameRight, frameTop),
                        end = Offset(frameRight - cornerLength, frameTop),
                        strokeWidth = cornerStrokeWidth
                    )

                    // bottom left
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameLeft, frameBottom - cornerLength),
                        end = Offset(frameLeft, frameBottom),
                        strokeWidth = cornerStrokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameLeft, frameBottom),
                        end = Offset(frameLeft + cornerLength, frameBottom),
                        strokeWidth = cornerStrokeWidth
                    )

                    // bottom right
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameRight, frameBottom - cornerLength),
                        end = Offset(frameRight, frameBottom),
                        strokeWidth = cornerStrokeWidth
                    )
                    drawLine(
                        color = cornerColor,
                        start = Offset(frameRight, frameBottom),
                        end = Offset(frameRight - cornerLength, frameBottom),
                        strokeWidth = cornerStrokeWidth
                    )
                }
        )
    }
}