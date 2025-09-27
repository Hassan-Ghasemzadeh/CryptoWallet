package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.receive

import android.content.ClipData
import android.content.Context
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.crypto.models.AddressParams
import com.softwarecleandevelopment.core.crypto.security.CryptoStore
import com.softwarecleandevelopment.crypto_chains.crypto_info.domain.model.CryptoInfo
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _address: MutableStateFlow<String> = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val _filteredTokens = MutableStateFlow<List<CryptoInfo>>(emptyList())
    val filteredTokens: StateFlow<List<CryptoInfo>> = _filteredTokens

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        val filteredTokens = if (query.isNotBlank()) {
            tokens.filter {
                it.name.contains(query, ignoreCase = true) || it.symbol.contains(
                    query, ignoreCase = true
                )
            }
        } else {
            tokens
        }
        _filteredTokens.value = filteredTokens
    }

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

    fun copyToClipBoard(coinId: String) {
        getAddress(coinId)
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText("Wallet Address", address.value)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}