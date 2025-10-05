package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.wallet_home

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.core.database.cache_datastore.CacheDataStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase.GetCryptoInfoUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class WalletHomeViewModel @Inject constructor(
    private val getCryptoInfoUseCase: GetCryptoInfoUseCase,
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val cryptoStore: CryptoStore,
    private val cryptoList: List<CoinInfo>,
    private val cache: CacheDataStore, // Not directly used in the final load logic here, but kept
) : ViewModel() {

    // ... (StateFlow declarations remain the same)
    private val _cryptos =
        MutableStateFlow<Resource<List<CoinInfo>>>(Resource.Success(data = emptyList()))
    val cryptos: StateFlow<Resource<List<CoinInfo>>> = _cryptos
    private val _cacheList =
        MutableStateFlow<List<CoinInfo>>(emptyList())
    val cacheList: StateFlow<List<CoinInfo>> = _cacheList
    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    init {
        viewModelScope.launch {
            fetchFromCache()
        }
    }

    /**
     * Public function called by the Pull-to-Refresh action.
     * This function's sole responsibility is to start the fetch and manage the 'isRefreshing' flag.
     */
    fun loadCryptoInfo() {
        viewModelScope.launch {
            _isRefreshing.value = true

            try {
                fetchAndDisplayCryptoInfo()
            } catch (e: Exception) {
                e.printStackTrace()
                _cryptos.value = Resource.Error(e.message ?: "Unknown error")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    /**
     * Private function to encapsulate the complex logic of fetching data,
     * which now must handle the flow and update the main _cryptos state.
     */
    private suspend fun fetchAndDisplayCryptoInfo() {
        val result = getActiveWalletUseCase.invoke(Unit)

        if (result !is Resource.Success) {
            _cryptos.value = Resource.Error("Failed to retrieve active wallet.")
            return
        }

        val wallet = result.data.first() ?: return // Handle null wallet
        val decryptedByteArray = Base64.decode(wallet.mnemonic, Base64.DEFAULT)
        val decryptedMnemonic = cryptoStore.decrypt(decryptedByteArray).toString(Charsets.UTF_8)

        val params = AddressParams(
            accountIndex = wallet.id.toInt(),
            mnemonic = decryptedMnemonic,
            passPhrase = "",
        )
        getCryptoInfoUseCase(params)
            .collect { resource ->
                _cryptos.value = resource

                if (resource is Resource.Success<List<CoinInfo>>) {

                    _cacheList.value = resource.data
                    getCryptosBalance(resource.data)
                    // The flow is expected to complete naturally here.
                }
            }
    }

    private fun getCryptosBalance(chains: List<CoinInfo>) {
        val totalBalance = chains.sumOf { it.balance }
        _balance.value = totalBalance
    }

    private fun fetchFromCache() {
        viewModelScope.launch {
            if (cryptoList.isEmpty()) {
                fetchAndDisplayCryptoInfo()
                return@launch
            }

            val cachedCoins = coroutineScope {
                cryptoList.map { coinInfo ->
                    // Start an asynchronous task for each coin
                    async {
                        cache.getCoin(coinInfo.symbol)
                    }
                }
            }.awaitAll() // Wait for all cache lookups to complete

            val list = cachedCoins.filterNotNull()

            if (list.isNotEmpty()) {
                _cacheList.value = list
                _cryptos.value = Resource.Success(data = list)
            } else {
                // Only fetch from network if the cache is completely empty
                fetchAndDisplayCryptoInfo()
            }
        }
    }
}