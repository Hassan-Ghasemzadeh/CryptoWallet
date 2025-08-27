package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Base64
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecretPhraseViewModel @Inject constructor(val cryptoStore: CryptoStore) : ViewModel() {

    private val _mnemonic = mutableStateOf("")
    val mnemonic: State<String> = _mnemonic
    fun getMnemonic(encryptedMnemonic: String?): List<String> {
        return if (encryptedMnemonic != null) {
            val decryptedByteArray = Base64.decode(encryptedMnemonic, Base64.DEFAULT)
            val decryptedMnemonic = cryptoStore.decrypt(decryptedByteArray)
            _mnemonic.value = decryptedMnemonic.toString(Charsets.UTF_8)
            val words = mnemonic.value.split(" ")
            words
        } else {
            listOf()
        }
    }

    fun copyToClipboard(context: Context) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Copied Text", mnemonic.value)
        clipboardManager.setPrimaryClip(clipData)
        // Optional: Show a toast message to the user
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}