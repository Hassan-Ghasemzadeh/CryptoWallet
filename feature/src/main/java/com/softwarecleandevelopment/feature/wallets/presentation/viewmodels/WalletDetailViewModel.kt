package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.feature.wallets.domain.models.UpdateWalletEvent
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletDetailViewModel @Inject constructor(
    val walletsRepository: WalletsRepository,
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name
    private val _navigation = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

    fun updateWalletName(event: UpdateWalletEvent) {
        _name.value = event.name
        viewModelScope.launch {
            val result = walletsRepository.updateWalletName(event.name, event.walletId)
            when (result) {
                is Resource.Error -> {}
                is Resource.Success<*> -> {}
            }
        }
    }
}