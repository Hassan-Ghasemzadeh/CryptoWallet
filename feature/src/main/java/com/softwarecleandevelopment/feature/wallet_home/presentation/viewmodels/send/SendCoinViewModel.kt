package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.send

import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SendCoinViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SendCoinUiState())
    val uiState = _uiState.asStateFlow()

    fun onAddressChanged(newAddress: String) {
        _uiState.value = _uiState.value.copy(address = newAddress)
    }

    fun onAmountChanged(newAmount: String) {
        _uiState.value = _uiState.value.copy(amount = newAmount)
    }

    fun onMaxClicked(balance: Double) {
        // Example: Fetch balance and set it
        _uiState.value = _uiState.value.copy(amount = "$balance")
    }

    // A function to get text from the clipboard
    fun getCopiedText(): String? {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        if (clipboardManager.hasPrimaryClip()) {
            val clipData = clipboardManager.primaryClip

            if (clipData != null && clipData.itemCount > 0) {
                val item = clipData.getItemAt(0)

                val text = item.text

                return text?.toString()
            }
        }

        return null
    }

}