package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.confirm_send

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendResult
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendState
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.EstimateNetworkFeeUseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.SendTokenUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class SendViewModel @Inject constructor(
    private val sendTokenUseCase: SendTokenUseCase,
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val estimateNetworkFeeUseCase: EstimateNetworkFeeUseCase,
    private val cryptoStore: CryptoStore,
) : ViewModel() {

    private val _state = MutableStateFlow(SendState())
    val state: StateFlow<SendState> = _state.asStateFlow()

    private val _privateKey = MutableStateFlow("")
    val privateKey: StateFlow<String> = _privateKey.asStateFlow()

    fun getPrivateKey() {
        viewModelScope.launch {
            getActiveWalletUseCase.invoke(Unit).let { result ->
                when (result) {
                    is Resource.Success -> handleWalletResult(result.data)
                    is Resource.Error -> handleWalletError(result.message)
                    is Resource.Loading -> handleWalletLoading() // Placeholder for loading state if needed
                }
            }
        }
    }

    private suspend fun handleWalletResult(walletFlow: Flow<WalletEntity?>) {
        try {
            walletFlow.collectLatest { wallet ->
                wallet?.privateKey?.let { encryptedKey ->
                    _privateKey.value = decryptPrivateKey(encryptedKey)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Consider updating a state flow for errors
        }
    }

    private fun handleWalletError(message: String?) {
        // Log the error or update the state with an error message
        println("Error fetching active wallet: $message")
        // _state.update { it.copy(error = message) }
    }

    private fun handleWalletLoading() {
        // Handle loading state if necessary
        // _state.update { it.copy(isLoading = true) }
    }

    private fun decryptPrivateKey(encodedPrivateKey: String): String {
        val encryptedBytes = Base64.decode(encodedPrivateKey, Base64.DEFAULT)
        val decryptedBytes = cryptoStore.decrypt(encryptedBytes)
        return Base64.encodeToString(decryptedBytes, Base64.DEFAULT)
    }

    //---------------------------------------------------------

    fun estimateFee(tokenContractAddress: String? = null) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                when (val result = estimateNetworkFeeUseCase.invoke(tokenContractAddress)) {
                    is Resource.Success -> handleFeeEstimationSuccess(
                        result.data,
                        tokenContractAddress
                    )

                    is Resource.Error -> handleFeeEstimationError(result.message)
                    is Resource.Loading -> {} // Not handled here, but could be.
                }
            } catch (e: Exception) {
                handleFeeEstimationError(e.message)
            }
        }
    }

    private fun handleFeeEstimationSuccess(
        feeData: Double,
        tokenContractAddress: String?
    ) {
        val feeInEther = feeData
        val feeDisplayString =
            "~${
                feeInEther.toBigDecimal().setScale(8, RoundingMode.HALF_UP)
            } ${if (tokenContractAddress.isNullOrEmpty()) "ETH/BNB" else "ETH/BNB (token)"}"
        _state.update {
            it.copy(
                isLoading = false,
                networkFeeUsd = feeDisplayString
            )
        }
    }

    private fun handleFeeEstimationError(message: String?) {
        _state.update { it.copy(isLoading = false, error = message) }
    }

    //---------------------------------------------------------

    fun send(event: SendTokenEvent) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, successTransactionHash = null) }
            sendTokenUseCase(event).collect { resultWrapper ->
                when (resultWrapper) {
                    is Resource.Error -> handleSendError(resultWrapper.message)
                    is Resource.Success -> handleSendSuccess(resultWrapper.data)
                    is Resource.Loading -> handleSendLoading()
                }
            }
        }
    }

    private fun handleSendSuccess(sendResult: SendResult) {
        // Update state with transaction hash or receipt
        _state.update {
            it.copy(
                isLoading = false,
                successTransactionHash = sendResult.transactionHash
            )
        }
    }

    private fun handleSendError(message: String?) {
        _state.update { it.copy(isLoading = false, error = message) }
    }

    private fun handleSendLoading() {
        _state.update { it.copy(isLoading = true) }
    }
}
