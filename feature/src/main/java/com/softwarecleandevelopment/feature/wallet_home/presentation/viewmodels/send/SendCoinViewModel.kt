package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.send

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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

    fun onMaxClicked() {
        // Example: Fetch balance and set it
        _uiState.value = _uiState.value.copy(amount = "1000")
    }

    fun getClipboardText(): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            ?: return null

        if (!clipboard.hasPrimaryClip() || !clipboard.primaryClipDescription?.hasMimeType(
                ClipDescription.MIMETYPE_TEXT_PLAIN
            )!!
        ) {
            return null
        }

        try {
            val primaryClip = clipboard.primaryClip ?: return null
            if (primaryClip.itemCount > 0) {
                val item = primaryClip.getItemAt(0)
                return item.text?.toString()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to read from clipboard.", Toast.LENGTH_SHORT).show()
        }
        return null
    }
}