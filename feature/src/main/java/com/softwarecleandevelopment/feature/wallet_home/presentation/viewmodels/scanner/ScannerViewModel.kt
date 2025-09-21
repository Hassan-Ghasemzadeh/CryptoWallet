package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.scanner

import android.content.Context
import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Executor
import javax.inject.Inject


@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scanner: BarcodeScanner,
    @ApplicationContext context: Context,
) : ViewModel() {
    private var cameraProvider: ProcessCameraProvider? = null
    private val executor: Executor by lazy { ContextCompat.getMainExecutor(context) }

    fun startScanning(
        lifecycleOwner: LifecycleOwner,
        context: Context,
        previewView: PreviewView,
        onScanned: (String) -> Unit
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()
                setupCamera(
                    lifecycleOwner,
                    previewView,
                    onScanned
                )
            },
            executor
        )
    }

    private fun setupCamera(
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        onScanned: (String) -> Unit
    ) {
        val preview = createPreview(previewView)
        val imageAnalysis = createImageAnalysis(onScanned)

        cameraProvider?.unbindAll()
        cameraProvider?.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            imageAnalysis
        )
    }

    private fun createPreview(previewView: PreviewView): Preview =
        Preview.Builder()
            .build()
            .also { it.setSurfaceProvider(previewView.surfaceProvider) }

    @OptIn(ExperimentalGetImage::class)
    private fun createImageAnalysis(onScanned: (String) -> Unit): ImageAnalysis =
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .apply {
                setAnalyzer(executor) { proxy ->
                    val mediaImage = proxy.image
                    if (mediaImage != null) {
                        processImage(
                            proxy,
                            proxy.imageInfo.rotationDegrees,
                            onScanned
                        )
                    }
                    proxy.close()
                }
            }

    @OptIn(ExperimentalGetImage::class)
    private fun processImage(
        mediaImage: ImageProxy,
        rotationDegrees: Int,
        onScanned: (String) -> Unit
    ) {
        val inputImage = mediaImage.image?.let { InputImage.fromMediaImage(it, rotationDegrees) }
        if (inputImage != null) {
            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.rawValue?.let { value ->
                        onScanned(value)
                        stopScanning()
                    }
                }
        }
    }

    fun stopScanning() {
        cameraProvider?.unbindAll()
        cameraProvider = null
    }
}