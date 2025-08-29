package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.core.common.utils.Resource
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.repository.WalletRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.usecases.CreateWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val createWalletUseCase: CreateWalletUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val walletCreatedKey = booleanPreferencesKey("wallet_created")

    fun createWallet(derived: Derived) {
        viewModelScope.launch {
            val result = createWalletUseCase.invoke(derived)
            when (result) {
                is Resource.Error -> {
                    _toastMessage.emit("Error creating wallet: ${result.message}")
                }

                is Resource.Success<*> -> {
                    dataStore.updateData {
                        it.toMutablePreferences().apply {
                            this[walletCreatedKey] = true
                        }
                    }
                    _toastMessage.emit("Wallet created successfully")
                }
            }
        }
    }
}