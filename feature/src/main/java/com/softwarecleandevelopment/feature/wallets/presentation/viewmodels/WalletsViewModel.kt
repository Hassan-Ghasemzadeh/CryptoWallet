package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallets.domain.usecase.SelectWalletUseCase
import com.softwarecleandevelopment.feature.wallets.domain.usecase.GetWalletsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WalletsViewModel @Inject constructor(
    private val selectWalletUseCase: SelectWalletUseCase,
    private val getWalletsUseCase: GetWalletsUseCase
) : ViewModel() {

    private val _wallets = MutableStateFlow<List<WalletEntity>>(listOf())
    val wallets: StateFlow<List<WalletEntity>> = _wallets
    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent: SharedFlow<Unit> = _navigationEvent.asSharedFlow()

    init {
        getWallets()
    }

    fun selectWallet(walletId: Long) {
        viewModelScope.launch {
            selectWalletUseCase.invoke(walletId)
        }
    }

    fun onWalletClicked(walletId: Long) {
        viewModelScope.launch {
            selectWallet(walletId)
            _navigationEvent.emit(Unit) // Emit a navigation event
        }
    }

    private fun getWallets() {
        viewModelScope.launch {
            val result = getWalletsUseCase.invoke(Unit)
            when (result) {
                is Resource.Error -> {
                    _wallets.value = listOf()
                }

                is Resource.Success<Flow<List<WalletEntity>>> -> {
                    result.data.collectLatest { response ->
                        _wallets.value = response
                    }
                }

                is Resource.Loading -> {

                }
            }
        }
    }
}