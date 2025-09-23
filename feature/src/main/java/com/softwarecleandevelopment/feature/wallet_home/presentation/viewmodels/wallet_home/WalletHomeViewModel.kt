package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.wallet_home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.usecases.GenerateEthAddressUseCase
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase.GetCryptoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _cryptos = MutableStateFlow<Resource<List<CryptoInfo>>>(Resource.Loading)
    val cryptos: StateFlow<Resource<List<CryptoInfo>>> = _cryptos
    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    init {
        loadCryptoInfo()
    }

    fun loadCryptoInfo() {
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                val ethAddressResult = getEthAddress()
                if (ethAddressResult is Resource.Success<String?>) {
                    val ethAddress = ethAddressResult.data
                    fetchAndDisplayCryptoInfo(ethAddress ?: "")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _cryptos.value = Resource.Error(e.message ?: "Unknown error")
            } finally {
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
        getCryptoInfoUseCase(ethAddress).collect {
            _cryptos.value = it
            if (it is Resource.Success<List<CryptoInfo>>) {
                getCryptosBalance(it.data)
            }
        }
    }

    private fun getCryptosBalance(chains: List<CryptoInfo>) {
        chains.forEach {
            var temp = 0.0
            temp += it.balance
            _balance.value = temp
        }
    }
}