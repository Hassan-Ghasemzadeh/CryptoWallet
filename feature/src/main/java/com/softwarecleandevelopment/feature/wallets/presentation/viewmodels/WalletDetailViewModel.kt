package com.softwarecleandevelopment.feature.wallets.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.core.database.room.models.WalletEntity
import com.softwarecleandevelopment.feature.wallets.domain.models.DeleteWalletEvent
import com.softwarecleandevelopment.feature.wallets.domain.models.UpdateWalletEvent
import com.softwarecleandevelopment.feature.wallets.domain.usecase.DeleteWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.softwarecleandevelopment.feature.wallets.domain.usecase.GetWalletsUseCase
import com.softwarecleandevelopment.feature.wallets.domain.usecase.SelectWalletUseCase
import com.softwarecleandevelopment.feature.wallets.domain.usecase.UpdateWalletUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class WalletDetailViewModel @Inject constructor(
    private val deleteWalletUseCase: DeleteWalletUseCase,
    private val selectWalletUseCase: SelectWalletUseCase,
    private val getWalletsUseCase: GetWalletsUseCase,
    private val updateWalletUseCase: UpdateWalletUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _wallets = MutableStateFlow<List<WalletEntity>>(listOf())
    val wallets: StateFlow<List<WalletEntity>> = _wallets

    private val _name = mutableStateOf("")
    val name: State<String> = _name
    private val _navigation =
        MutableSharedFlow<DeleteWalletEvent>(replay = 0, extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()
    private val walletCreatedKey = booleanPreferencesKey("wallet_created")

    fun deleteWallet(walletId: Long) {
        viewModelScope.launch {
            val result = deleteWalletUseCase.invoke(walletId)
            when (result) {
                is Resource.Error -> {}
                is Resource.Success<*> -> {
                    val result = getWalletsUseCase.invoke(Unit)
                    when (result) {
                        is Resource.Error -> {
                            _wallets.value = listOf()
                        }

                        is Resource.Success<Flow<List<WalletEntity>>> -> {
                            result.data.collectLatest { response ->
                                _wallets.value = response
                                if (response.isEmpty()) {
                                    _navigation.emit(DeleteWalletEvent.NavigateToCreateWallet)
                                    dataStore.updateData {
                                        it.toMutablePreferences().apply {
                                            this[walletCreatedKey] = false
                                        }
                                    }
                                } else {
                                    selectWallet()
                                }
                            }
                        }

                        Resource.Loading -> {}
                    }
                }

                Resource.Loading -> {}
            }

        }
    }


    fun selectWallet() {
        val walletId = _wallets.value.firstOrNull()?.id ?: return
        viewModelScope.launch {
            selectWalletUseCase.invoke(walletId)
        }
    }


    fun updateWalletName(event: UpdateWalletEvent) {
        _name.value = event.name
        viewModelScope.launch {
            val result = updateWalletUseCase.invoke(event)
            when (result) {
                is Resource.Error -> {}
                is Resource.Success<*> -> {}
                is Resource.Loading -> {

                }
            }
        }
    }
}