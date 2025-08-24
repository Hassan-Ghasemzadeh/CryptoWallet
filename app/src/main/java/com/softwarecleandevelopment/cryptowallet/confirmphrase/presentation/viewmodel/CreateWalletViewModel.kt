package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Result
import com.softwarecleandevelopment.cryptowallet.confirmphrase.data.source.WalletRepositoryImpl
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.usecases.CreateWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val repositoryImpl: WalletRepositoryImpl,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val walletCreatedKey = booleanPreferencesKey("wallet_created")

    fun createWallet(derived: Derived) {
        viewModelScope.launch {
            val useCase = CreateWalletUseCase(repositoryImpl)
            val result = useCase.invoke(derived)
            when (result) {
                is Result.Error -> {
                    _toastMessage.emit("Error creating wallet: ${result.exception.message}")
                }

                is Result.Success<*> -> {
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