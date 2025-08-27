package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import android.util.Base64
import androidx.lifecycle.ViewModel
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecretPhraseViewModel @Inject constructor(val cryptoStore: CryptoStore) : ViewModel() {

    fun getMnemonic(encryptedMnemonic: String?): List<String> {
        return if (encryptedMnemonic != null) {
            val decryptedByteArray = Base64.decode(encryptedMnemonic, Base64.DEFAULT)
            val decryptedMnemonic = cryptoStore.decrypt(decryptedByteArray)
            val words = decryptedMnemonic.toString(Charsets.UTF_8).split(" ")
            words
        } else {
            listOf()
        }
    }

}