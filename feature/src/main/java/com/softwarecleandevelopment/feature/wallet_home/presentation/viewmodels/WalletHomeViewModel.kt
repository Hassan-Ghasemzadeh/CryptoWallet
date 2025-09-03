package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.ethereum.data.model.initialCryptos
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.GetCryptoInfoEvent
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GenerateEthAddressUseCase
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GetCryptoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class WalletHomeViewModel @Inject constructor(
    private val getCryptoInfoUseCase: GetCryptoInfoUseCase,
    private val generateEthAddressUseCase: GenerateEthAddressUseCase,
) : ViewModel() {
    private val _cryptos: MutableState<List<CryptoInfo>> = mutableStateOf(listOf())
    val cryptos: State<List<CryptoInfo>> = _cryptos

    init {
        loadCryptoInfo()
    }

    fun loadCryptoInfo() {
        viewModelScope.launch {
            val generate = generateEthAddressUseCase.invoke(Unit)
            when (generate) {
                is Resource.Error -> {

                }

                is Resource.Success<Flow<String?>> -> {
                    val result = getCryptoInfoUseCase.invoke(
                        GetCryptoInfoEvent(
                            initialCryptos,
                            generate.data.first().toString()

                        )
                    )
                    when (result) {
                        is Resource.Error -> {
                            _cryptos.value = listOf()
                        }

                        is Resource.Success<Flow<List<CryptoInfo>>> -> {
                            result.data.collectLatest { cryptoInfos ->
                                _cryptos.value = cryptoInfos
                            }
                        }
                    }
                }
            }
        }
    }
}