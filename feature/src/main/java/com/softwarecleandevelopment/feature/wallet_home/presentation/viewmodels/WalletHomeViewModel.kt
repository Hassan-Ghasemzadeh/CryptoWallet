package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.model.initialCryptos
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.GetCryptoInfoEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GetCryptoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletHomeViewModel @Inject constructor(
    val getCryptoInfoUseCase: GetCryptoInfoUseCase,
) : ViewModel() {
    private val _cryptos: MutableState<List<CryptoInfo>> = mutableStateOf(listOf())
    val crypto: State<List<CryptoInfo>> = _cryptos

    init {
        getCryptoInfo(
            GetCryptoInfoEvent(
                initialCryptos,
                "ethereum",
            )
        )
    }

    fun getCryptoInfo(event: GetCryptoInfoEvent) {
        viewModelScope.launch {
            val result = getCryptoInfoUseCase.invoke(event)
            when (result) {
                is Resource.Error -> {
                    _cryptos.value = listOf()
                }

                is Resource.Success<Flow<List<CryptoInfo>>> -> {
                    result.data.collectLatest {
                        _cryptos.value = it
                    }
                }
            }
        }
    }
}