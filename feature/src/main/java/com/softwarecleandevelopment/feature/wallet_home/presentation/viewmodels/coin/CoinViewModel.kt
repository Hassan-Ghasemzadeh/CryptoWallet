package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.coin

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.TransactionParams
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.usecase.GetTransactionUseCase
import com.softwarecleandevelopment.feature.wallet_home.domain.models.ReceiveNavigationParams
import com.softwarecleandevelopment.feature.wallet_home.domain.models.SendNavigationParams
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val cryptoStore: CryptoStore,
    val tokens: List<CoinInfo>,
) : ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>?>(emptyList())
    val transactions: StateFlow<List<Transaction>?> = _transactions.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CoinNavigation>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    /**
     * this function should be changed in future.do nothing for now
     */
    fun onSwapClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(
                CoinNavigation.OnSwapClicked
            )
        }
    }

    fun onReceiveClicked(coin: CoinInfo?) {
        viewModelScope.launch {
            if (coin != null) {
                val generatedAddress = getAddress(coin.id)
                _address.value = generatedAddress
                if (generatedAddress.isNotEmpty()) {
                    _navigationEvent.emit(
                        CoinNavigation.NavigateToReceive(
                            ReceiveNavigationParams(
                                address = generatedAddress,
                                title = coin.symbol
                            ),
                        )
                    )
                }
            }
        }
    }

    fun onSendClicked(coin: CoinInfo?) {
        viewModelScope.launch {
            if (coin != null) {
                _navigationEvent.emit(
                    CoinNavigation.NavigateToSend(
                        SendNavigationParams(
                            balance = coin.balance,
                            coin = coin.symbol
                        ),
                    )
                )
            }
        }
    }

    fun fetchTransactions(coin: CoinInfo?) {
        viewModelScope.launch {
            if (coin != null) {
                val address = getAddress(coin.id)
                _address.value = address
                if (address.isNotEmpty()) {
                    val result = getTransactionUseCase.invoke(
                        params = TransactionParams(
                            address = address,
                            coin = coin.symbol.lowercase(),
                        )
                    )
                    _transactions.value = result
                }
            }
        }
    }

    /**
     * Asynchronously retrieves a coin address using the active wallet's mnemonic.
     * Returns the address on success, or an empty string (or considers throwing an exception) on failure.
     *
     * @return The generated coin address string, or an empty string on failure.
     */
    private suspend fun getAddress(coinId: String): String {
        val coin = tokens.find { it.id == coinId }

        val activeWalletResource = getActiveWalletUseCase.invoke(Unit)
        val wallet = (activeWalletResource as Resource.Success).data.first()

        val mnemonic = decryptMnemonic(wallet?.mnemonic ?: "")


        val addressParams = AddressParams(
            mnemonic = mnemonic,
            accountIndex = wallet?.id?.toInt() ?: 0,
            passPhrase = "",
        )

        val addressResource = coin?.generator?.invoke(addressParams)

        return (addressResource as Resource.Success).data
    }

    // Private helper to isolate the sensitive, error-prone decryption logic.
    private fun decryptMnemonic(base64Mnemonic: String): String {
        val decryptedByteArray = Base64.decode(base64Mnemonic, Base64.DEFAULT)
        val decryptedMnemonic = cryptoStore.decrypt(decryptedByteArray)
        return decryptedMnemonic.toString(Charsets.UTF_8)
    }

}