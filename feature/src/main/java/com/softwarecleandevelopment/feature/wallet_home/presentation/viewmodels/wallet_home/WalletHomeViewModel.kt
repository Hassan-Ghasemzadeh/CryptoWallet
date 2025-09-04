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
import kotlinx.coroutines.flow.Flow
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
            val generate = generateEthAddressUseCase.invoke(Unit)
            when (generate) {
                is Resource.Error -> {}

                is Resource.Success<Flow<String?>> -> {
                    val result = getCryptoInfoUseCase.invoke(
                        GetCryptoInfoEvent(
                            initialCryptos, generate.data.first().toString()

                        )
                    )
                    when (result) {
                        is Resource.Error -> {
                            _cryptos.value = UiState.Error(result.message)

                            _isRefreshing.value = false
                        }

                        is Resource.Success<Flow<List<CryptoInfo>>> -> {
                            _cryptos.value = UiState.Success(result.data.first())

                            _isRefreshing.value = false
                        }
                    }
                }
            }
        }
    }
}