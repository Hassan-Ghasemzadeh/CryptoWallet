package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiveTokensViewModel @Inject constructor(
    val tokens: List<CryptoInfo>,
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
    private val cryptoStore: CryptoStore,
) : ViewModel() {
    private val _address: MutableStateFlow<String> = MutableStateFlow("")
    val address: StateFlow<String> = _address

    fun getAddress(coinId: String) {
        val coin = tokens.find { it.id == coinId }
        viewModelScope.launch {
            val activeWallet = getActiveWalletUseCase.invoke(Unit)
            if (activeWallet is Resource.Success) {
                activeWallet.data.collectLatest { wallet ->
                    wallet?.let {
                        val decryptedByteArray = Base64.decode(wallet.mnemonic, Base64.DEFAULT)
                        val decryptedMnemonic = cryptoStore.decrypt(decryptedByteArray)
                        val mnemonic = decryptedMnemonic.toString(Charsets.UTF_8)
                        val result = coin?.generator?.invoke(
                            AddressParams(
                                mnemonic = mnemonic,
                                accountIndex = wallet.id.toInt(),
                                passPhrase = "",
                            )
                        )
                        if (result is Resource.Success) {
                            _address.value = result.data
                        }
                    }
                }
            }
        }
    }

}