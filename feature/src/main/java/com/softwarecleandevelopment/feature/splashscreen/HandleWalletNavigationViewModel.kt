package com.softwarecleandevelopment.feature.splashscreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HandleWalletNavigationViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val walletCreatedKey = booleanPreferencesKey("wallet_created")

    fun isWalletCreated(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[walletCreatedKey] ?: false
        }
    }
}