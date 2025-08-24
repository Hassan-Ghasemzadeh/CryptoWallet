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
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.PhraseRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.GeneratePhraseUseCase
import kotlinx.coroutines.launch

@HiltViewModel
class RecoveryPhraseViewModel @Inject constructor(
    private val walletRepository: PhraseRepositoryImpl,
) : ViewModel() {
    private var _mnemonic = mutableStateOf("")
    val mnemonic: State<String> = _mnemonic

    private var _phraseList = mutableStateOf(listOf<String>())
    val phraseList: State<List<String>> = _phraseList

    private var _derived = mutableStateOf(Derived())
    val derived: State<Derived> = _derived

    init {
        generateWallet()
    }

    fun generateWallet() {
        viewModelScope.launch {
            val generateWalletUseCase = GeneratePhraseUseCase(repository = walletRepository)
            val derived = generateWalletUseCase(Unit)
            _mnemonic.value = derived.mnemonic
            _phraseList.value = derived.mnemonic.split(" ")
            _derived.value = derived
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