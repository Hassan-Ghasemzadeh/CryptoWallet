package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.wallet_home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.common.utils.UiState
import com.softwarecleandevelopment.crypto_chains.ethereum.data.model.initialCryptos
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.GetCryptoInfoEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GenerateEthAddressUseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GetCryptoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class WalletHomeViewModel @Inject constructor(
    private val getCryptoInfoUseCase: GetCryptoInfoUseCase,
    private val generateEthAddressUseCase: GenerateEthAddressUseCase,
) : ViewModel() {
    private val _cryptos = MutableStateFlow<UiState<List<CryptoInfo>>>(UiState.Loading)
    val cryptos: StateFlow<UiState<List<CryptoInfo>>> = _cryptos
    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        loadCryptoInfo()
    }

    fun loadCryptoInfo() {
        viewModelScope.launch {
            _cryptos.value = UiState.Loading
            _isRefreshing.value = true

            try {
                val ethAddressResult = getEthAddress()
                when (ethAddressResult) {
                    is Resource.Error -> {

                    }

                    Resource.Loading -> {

                    }

                    is Resource.Success<String?> -> {
                        val ethAddress = ethAddressResult.data
                        fetchAndDisplayCryptoInfo(ethAddress ?: "")
                    }
                }

            } catch (e: Exception) {
                // Catch any unexpected exceptions
                handleError(e.message)
            } finally {
                // Always set refreshing to false
                _isRefreshing.value = false
            }
        }
    }

    private suspend fun getEthAddress(): Resource<String?> {
        // Responsible for a single task: getting the address
        return when (val result = generateEthAddressUseCase(Unit)) {
            is Resource.Success -> Resource.Success(result.data.first())
            is Resource.Error -> Resource.Error(result.message)
            Resource.Loading -> Resource.Loading // Not expected in this flow but good practice
        }
    }

    private suspend fun fetchAndDisplayCryptoInfo(ethAddress: String) {
        // Responsible for fetching and updating UI with crypto info
        val cryptoInfoResult = getCryptoInfoUseCase(
            GetCryptoInfoEvent(initialCryptos, ethAddress)
        )

        when (cryptoInfoResult) {
            is Resource.Success -> {
                _cryptos.value = UiState.Success(cryptoInfoResult.data.first())
            }

            is Resource.Error -> {
                handleError(cryptoInfoResult.message)
            }

            Resource.Loading -> {} // Not expected in this flow
        }
    }

    private fun handleError(message: String?) {
        _cryptos.value = UiState.Error(message ?: "An unknown error occurred.")
    }
}