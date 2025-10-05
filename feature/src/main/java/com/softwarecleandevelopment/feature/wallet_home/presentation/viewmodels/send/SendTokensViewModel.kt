package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.feature.wallet_home.domain.models.SendNavigationParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendTokensViewModel @Inject constructor(
    private val tokens: List<CoinInfo>,
) : ViewModel() {
    private val _filteredTokens = MutableStateFlow<List<CoinInfo>>(emptyList())
    val filteredTokens: StateFlow<List<CoinInfo>> = _filteredTokens

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _navigateToSend = MutableSharedFlow<SendNavigationParams>()
    val navigateToSend = _navigateToSend.asSharedFlow()

    init {
        onSearchQueryChanged("")
    }


    fun onTokenSelected(coinId: String) {
        viewModelScope.launch {
            val coin = tokens.find {
                it.id == coinId
            }

            _navigateToSend.emit(
                SendNavigationParams(
                    balance = coin?.balance ?: 0.0,
                    coin = coin?.symbol ?: "Coin"
                )
            )
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

}