package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallets.domain.usecase.SelectWalletUseCase
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletsViewModel @Inject constructor(val walletsRepository: WalletsRepository) : ViewModel() {

    private val _wallets = mutableStateOf<List<WalletEntity>>(listOf())
    val wallets: State<List<WalletEntity>> = _wallets

    init {
        getWallets()
    }

    fun selectWallet(walletId: Long) {
        viewModelScope.launch {
            val result = SelectWalletUseCase(walletsRepository)
            result.invoke(walletId)
        }
    }

    fun getWallets() {
        viewModelScope.launch {
            val result = walletsRepository.getWallets()
            when (result) {
                is Resource.Error -> {
                    _wallets.value = listOf()
                }

                is Resource.Success<Flow<List<WalletEntity>>> -> {
                    result.data.collectLatest { response ->
                        _wallets.value = response
                    }
                }
            }
        }
    }
}