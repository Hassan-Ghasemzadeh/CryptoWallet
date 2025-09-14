package com.softwarecleandevelopment.feature.wallet_home.domain.usecases


import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import javax.inject.Inject

class GenerateQrBitmapUseCase @Inject constructor() {

    operator fun invoke(
        data: String, sizePx: Int = 1024, cornerRadiusPx: Int = 50 // New parameter for corner radius
    ): Bitmap {
        val hints = mapOf(
            EncodeHintType.MARGIN to 1  // thin border
        )
        val matrix: BitMatrix =
            MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, sizePx, sizePx, hints)

        val bitmap = createBitmap(matrix.width, matrix.height)

        // Calculate the half-width/height for convenience
        val halfWidth = matrix.width / 2
        val halfHeight = matrix.height / 2

        for (x in 0 until matrix.width) {
            for (y in 0 until matrix.height) {
                val color = if (matrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()

                // Apply corner rounding if cornerRadiusPx > 0
                if (cornerRadiusPx > 0) {
                    val isTransparent = isOutsideRoundedCorner(x, y, matrix.width, matrix.height, cornerRadiusPx)
                    if (isTransparent) {
                        bitmap[x, y] = 0x00000000 // Transparent
                    } else {
                        bitmap[x, y] = color
                    }
                } else {
                    bitmap[x, y] = color
                }
            }
        }
        return bitmap
    }

    /**
     * Checks if a given pixel (x, y) falls outside the rounded corners of a rectangle.
     * This function makes the outside of the corner transparent.
     */
    private fun isOutsideRoundedCorner(
        x: Int, y: Int,
        width: Int, height: Int,
        radius: Int
    ): Boolean {
        // Top-left corner
        if (x < radius && y < radius) {
            return (radius - x) * (radius - x) + (radius - y) * (radius - y) > radius * radius
        }
        // Top-right corner
        if (x > width - radius && y < radius) {
            return (x - (width - radius)) * (x - (width - radius)) + (radius - y) * (radius - y) > radius * radius
        }
        // Bottom-left corner
        if (x < radius && y > height - radius) {
            return (radius - x) * (radius - x) + (y - (height - radius)) * (y - (height - radius)) > radius * radius
        }
        // Bottom-right corner
        if (x > width - radius && y > height - radius) {
            return (x - (width - radius)) * (x - (width - radius)) + (y - (height - radius)) * (y - (height - radius)) > radius * radius
        }
        return false // Not in a corner that should be rounded off
    }
}