package com.softwarecleandevelopment.feature.wallet_home.domain.usecases

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class CopyToClipboardUseCase @Inject constructor(@ApplicationContext private val appContext: Context) {
    operator fun invoke(label: String, text: String) {
        val cm = appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, text))
    }
}