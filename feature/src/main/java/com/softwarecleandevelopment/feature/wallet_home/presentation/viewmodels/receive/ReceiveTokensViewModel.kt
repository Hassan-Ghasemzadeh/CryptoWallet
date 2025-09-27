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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
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

    private val _navigateToAddress = MutableSharedFlow<String>()
    val navigateToAddress = _navigateToAddress.asSharedFlow()
    init {
        onSearchQueryChanged("")
    }


    fun onTokenSelected(coinId: String) {
        viewModelScope.launch {
            val generatedAddress = getAddress(coinId)

            // A. Update the primary, persistent state IMMEDIATELY (for display if needed)
            _address.value = generatedAddress

            // B. Emit the one-time event for navigation (if successful)
            if (generatedAddress.isNotEmpty()) {
                _navigateToAddress.emit(generatedAddress)
            }
        }
    }


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

    /**
     * Asynchronously retrieves a coin address using the active wallet's mnemonic.
     * Returns the address on success, or an empty string (or considers throwing an exception) on failure.
     *
     * @param coinId The unique identifier of the cryptocurrency (e.g., "ethereum").
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

    fun copyToClipBoard(coinId: String) {
        viewModelScope.launch {
            val address = getAddress(coinId)
            _address.value = address
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Wallet Address", address)
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}