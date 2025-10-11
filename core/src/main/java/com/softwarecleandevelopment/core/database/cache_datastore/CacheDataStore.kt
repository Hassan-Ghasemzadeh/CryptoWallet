package com.softwarecleandevelopment.core.database.cache_datastore

import android.content.Context
import com.softwarecleandevelopment.core.common.utils.DataStoreManager // Import DataStoreManager
import com.softwarecleandevelopment.core.crypto.models.CoinInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CacheDataStore @Inject constructor(
    @ApplicationContext private val context: Context // Inject Context to use DataStoreManager
) {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private fun keyNameFor(symbol: String) = "coin_$symbol"

    suspend fun saveCoin(coin: CoinInfo) {
        withContext(Dispatchers.IO) {
            val keyName = keyNameFor(coin.symbol)
            val coinJsonString = json.encodeToString(coin)

            DataStoreManager.put(
                context = context,
                keyName = keyName,
                value = coinJsonString
            )
        }
    }

    suspend fun getCoin(symbol: String): CoinInfo? = withContext(Dispatchers.IO) {
        val keyName = keyNameFor(symbol)

        val coinJsonString = DataStoreManager.get<String?>(
            context = context,
            keyName = keyName,
            default = null // The String type must be nullable if the default is null
        )

        coinJsonString?.let {
            json.decodeFromString<CoinInfo>(it)
        }
    }


    suspend fun clearAll() {
        DataStoreManager.clearAll(context)
    }
}