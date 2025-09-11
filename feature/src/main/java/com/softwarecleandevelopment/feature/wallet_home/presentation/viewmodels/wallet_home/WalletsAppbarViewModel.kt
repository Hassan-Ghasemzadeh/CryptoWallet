package com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.wallet_home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallet_home.domain.usecases.GetActiveWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletsAppbarViewModel @Inject constructor(
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    init {
        getActiveWallet()
    }

    fun getActiveWallet() {
        viewModelScope.launch {
            val result = getActiveWalletUseCase.invoke(Unit)
            when (result) {
                is Resource.Error -> {
                    _name.value = result.message
                }

                is Resource.Success<Flow<WalletEntity?>> -> {
                    result.data.collectLatest { response ->
                        _name.value = response?.name ?: ""
                    }
                }

                Resource.Loading -> {

                }
            }
        }
    }
}