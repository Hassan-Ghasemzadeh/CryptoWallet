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
        data: String, sizePx: Int = 1024
    ): Bitmap {
        val hints = mapOf(
            EncodeHintType.MARGIN to 1  // thin border
        )
        val matrix: BitMatrix =
            MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, sizePx, sizePx, hints)

        val bitmap = createBitmap(matrix.width, matrix.height)
        for (x in 0 until matrix.width) {
            for (y in 0 until matrix.height) {
                bitmap[x, y] = if (matrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
            }
        }
        return bitmap
    }
}