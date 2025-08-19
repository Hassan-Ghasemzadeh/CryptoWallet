package com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.WalletRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.GenerateWalletUseCase
import kotlinx.coroutines.launch

@HiltViewModel
class RecoveryPhraseViewModel @Inject constructor(
    private val walletRepository: WalletRepositoryImpl,
) : ViewModel() {
    private var _mnemonic = mutableStateOf("")
    val mnemonic: State<String> = _mnemonic

    private var _phraseList = mutableStateOf(listOf<String>())
    val phraseList: State<List<String>> = _phraseList

    init {
        generateWallet()
    }

    fun generateWallet() {
        viewModelScope.launch {
            val generateWalletUseCase = GenerateWalletUseCase(repository = walletRepository)
            val wallet = generateWalletUseCase()
            _mnemonic.value = wallet.mnemonic
            _phraseList.value = wallet.mnemonic.split(" ")
        }
    }

    fun copyToClipboard(context: Context, text: String, label: String = "Copied Text") {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clipData)

        // Optional: Show a toast message to the user
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}