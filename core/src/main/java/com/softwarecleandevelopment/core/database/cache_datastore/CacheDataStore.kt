package com.softwarecleandevelopment.core.database.cache_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import com.softwarecleandevelopment.core.di.modules.CryptoCacheDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import kotlin.let

class CacheDataStore @Inject constructor(
    @CryptoCacheDataStore private val dataStore: DataStore<Preferences>,
) {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    //main key to save every coin
    private fun keyFor(symbol: String) = stringPreferencesKey("coin_$symbol")

    suspend fun saveCoin(coin: CoinInfo) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[keyFor(coin.symbol)] = json.encodeToString(coin)
            }
        }
    }

    suspend fun getCoin(symbol: String): CoinInfo? = withContext(Dispatchers.IO) {
        val prefs = dataStore.data.first()
        prefs[keyFor(symbol)]?.let {
            json.decodeFromString<CoinInfo>(it)
        }
    }

    suspend fun clearCoin(symbol: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it.remove(keyFor(symbol))
            }
        }
    }

    suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it.clear()
            }
        }
    }
}