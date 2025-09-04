package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.send

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SendCoinViewModel @Inject constructor() : ViewModel() {

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
}