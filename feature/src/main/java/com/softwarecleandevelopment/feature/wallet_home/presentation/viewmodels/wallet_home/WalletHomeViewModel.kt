package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.wallet_home

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CoinInfo
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase.GetCryptoInfoUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
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
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val cryptoStore: CryptoStore,
) : ViewModel() {
    private val _cryptos = MutableStateFlow<Resource<List<CoinInfo>>>(Resource.Loading)
    val cryptos: StateFlow<Resource<List<CoinInfo>>> = _cryptos
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
                fetchAndDisplayCryptoInfo()
            } catch (e: Exception) {
                e.printStackTrace()
                _cryptos.value = Resource.Error(e.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private suspend fun fetchAndDisplayCryptoInfo() {
        // Responsible for fetching and updating UI with crypto info
        val result = getActiveWalletUseCase.invoke(Unit)
        if (result is Resource.Success) {
            val wallet = result.data.first()
            val decryptedByteArray = Base64.decode(wallet?.mnemonic, Base64.DEFAULT)
            val decryptedMnemonic = cryptoStore.decrypt(decryptedByteArray)
            val mnemonic = decryptedMnemonic.toString(Charsets.UTF_8)
            val params = AddressParams(
                accountIndex = wallet?.id?.toInt() ?: 0,
                mnemonic = mnemonic,
                passPhrase = "",
            )
            getCryptoInfoUseCase(params).collect {
                _cryptos.value = it
                if (it is Resource.Success<List<CoinInfo>>) {
                    getCryptosBalance(it.data)
                }
            }
        }
    }

    private fun getCryptosBalance(chains: List<CoinInfo>) {
        chains.forEach {
            var temp = 0.0
            temp += it.balance
            _balance.value = temp
        }
    }
}