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
import com.softwarecleandevelopment.feature.wallets.domain.repository.WalletsRepository
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

@HiltViewModel
class WalletDetailViewModel @Inject constructor(
    val walletsRepository: WalletsRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name
    private val _navigation =
        MutableSharedFlow<DeleteWalletEvent>(replay = 0, extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()
    private val _wallets = mutableStateOf<List<WalletEntity>>(listOf())
    private val wallets: State<List<WalletEntity>> = _wallets
    private val walletCreatedKey = booleanPreferencesKey("wallet_created")


    fun deleteWallet(walletId: Long) {
        viewModelScope.launch {
            val usecase = DeleteWalletUseCase(walletsRepository)
            val result = usecase.invoke(walletId)
            when (result) {
                is Resource.Error -> {}
                is Resource.Success<*> -> {
                    val result = walletsRepository.getWallets()
                    when (result) {
                        is Resource.Error -> {
                            _wallets.value = listOf()
                        }

                        is Resource.Success<Flow<List<WalletEntity>>> -> {
                            result.data.collectLatest { response ->
                                if (response.isEmpty()) {
                                    _navigation.emit(DeleteWalletEvent.NavigateToCreateWallet)
                                    dataStore.updateData {
                                        it.toMutablePreferences().apply {
                                            this[walletCreatedKey] = false
                                        }
                                    }
                                } else {
                                    _navigation.emit(DeleteWalletEvent.NavigateBack)
                                }
                            }
                        }
                    }
                }
            }

        }
    }


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