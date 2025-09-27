package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive

import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.CopyToClipboardUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GenerateQrBitmapUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.ShareTextUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiveCoinViewModel @Inject constructor(
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val generateQrBitmap: GenerateQrBitmapUseCase,
    private val copyToClipboard: CopyToClipboardUseCase,
    private val shareText: ShareTextUseCase,
) : ViewModel() {

    private val _ui = MutableStateFlow(
        ReceiveCoinUiState(
            walletName = "",
            address = ""
        )
    )
    val ui: StateFlow<ReceiveCoinUiState> = _ui
    private val _navigateBack = MutableStateFlow(
        false
    )
    val navigateBack: StateFlow<Boolean> = _navigateBack

    fun generateQrCode(address: String) {
        viewModelScope.launch((Dispatchers.Default)) {
            val result = getActiveWalletUseCase.invoke(Unit)
            val wallet = (result as Resource.Success).data.first()
            _ui.value = ReceiveCoinUiState(
                walletName = wallet?.name ?: "",
                address = address,
            )
            // CHECK: If the address is NOT empty, proceed with QR generation
            if (address.isNotEmpty()) {
                // Only run the QR generation if the address is valid!
                val qrBitmap = generateQrBitmap(address)

                // Update the UI state with the QR
                _ui.update { it.copy(qr = qrBitmap) }
            } else {
                // Handle the failure where getAddress returned an empty string
                _ui.update { it.copy(toastMessage = "Could not generate address.") }
            }
        }
    }

    fun onEvent(e: ReceiveCoinEvent) {
        when (e) {
            ReceiveCoinEvent.OnBackClick -> {
                _navigateBack.value = true
            }

            ReceiveCoinEvent.OnCopyClick -> {
                copyToClipboard("ETH Address", _ui.value.address)
                consumeToast(toastMessage = "Address copied")
            }

            ReceiveCoinEvent.OnShareClick -> {
                shareText(_ui.value.address)
            }
        }
    }

    fun consumeToast(toastMessage: String) {
        _ui.value = _ui.value.copy(toastMessage = toastMessage)
    }
}