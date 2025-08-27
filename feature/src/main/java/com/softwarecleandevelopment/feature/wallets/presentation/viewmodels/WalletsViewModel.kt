package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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

    private val result = SelectWalletUseCase(walletsRepository)

    init {
        getWallets()
    }

    fun selectWallet(walletId: Long, navController: NavController) {
        viewModelScope.launch {
            result.invoke(walletId)
            navController.navigateUp()
        }
    }

    fun selectDefaultWallet() {
        // Check if the wallets list is not empty and if a wallet is not already active.
        viewModelScope.launch {
            val walletsList = _wallets.value
            if (walletsList.isNotEmpty() && walletsList.none { it.isActive }) {
                // If no wallet is active, select the first one in the list.
                result.invoke(walletsList.first().id)
            }
        }
    }

    private fun getWallets() {
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