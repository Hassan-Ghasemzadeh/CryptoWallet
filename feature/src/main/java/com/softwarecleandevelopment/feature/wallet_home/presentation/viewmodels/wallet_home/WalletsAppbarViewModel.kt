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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletsAppbarViewModel @Inject constructor(
    private val getActiveWalletUseCase: GetActiveWalletUseCase,
) : ViewModel() {
    private val _name = mutableStateOf("")
    val name: State<String> = _name

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
                    result.data.collect { response ->
                        _name.value = response?.name ?: ""
                    }
                }
            }
        }
    }
}