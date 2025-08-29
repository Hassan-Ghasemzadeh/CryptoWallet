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
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.data.repository.PhraseRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.domain.usecases.GeneratePhraseUseCase
import kotlinx.coroutines.launch

@HiltViewModel
class RecoveryPhraseViewModel @Inject constructor(
    private val generatePhraseUseCase: GeneratePhraseUseCase,
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
            val derived = generatePhraseUseCase.invoke(Unit)
            when (derived) {
                is Resource.Error -> {
                    _mnemonic.value = derived.message
                }

                is Resource.Success<Derived> -> {
                    _mnemonic.value = derived.data.mnemonic
                    _phraseList.value = derived.data.mnemonic.split(" ")
                    _derived.value = derived.data
                }
            }
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