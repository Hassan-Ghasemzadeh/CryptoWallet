package com.softwarecleandevelopment.feature.wallet_home.domain.usecases

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class ShareTextUseCase @Inject constructor(private val appContext: Context) {
    operator fun invoke(text: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(sendIntent, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appContext.startActivity(chooser)
    }
}