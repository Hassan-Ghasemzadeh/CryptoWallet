package com.softwarecleandevelopment.core.common.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.datastore by preferencesDataStore("setting_prefs")

object DataStoreManager {

    // Corrected DataStoreManager.kt
    @Suppress("UNCHECKED_CAST")
    suspend inline fun <reified T> put(
        context: Context,
        keyName: String,
        value: T,
    ) {
        val key = getPreferencesKey<T>(keyName)

        context.datastore.edit { preferences ->
            when (T::class) {
                Int::class -> preferences[key as Preferences.Key<Int>] = value as Int
                String::class -> preferences[key as Preferences.Key<String>] = value as String
                Boolean::class -> preferences[key as Preferences.Key<Boolean>] = value as Boolean
                Float::class -> preferences[key as Preferences.Key<Float>] = value as Float
                Long::class -> preferences[key as Preferences.Key<Long>] = value as Long

                else -> {
                    throw IllegalArgumentException("Unsupported type: ${T::class.simpleName}")
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend inline fun <reified T> get(
        context: Context,
        keyName: String,
        default: T
    ): T {
        val key = getPreferencesKey<T>(keyName)

        return context.datastore.data.map { preferences ->
            val value: Any? = when (T::class) {
                Int::class -> preferences[key as Preferences.Key<Int>] ?: default
                String::class -> preferences[key as Preferences.Key<String>] ?: default
                Boolean::class -> preferences[key as Preferences.Key<Boolean>] ?: default
                Float::class -> preferences[key as Preferences.Key<Float>] ?: default
                Long::class -> preferences[key as Preferences.Key<Long>] ?: default

                else -> {
                    throw IllegalArgumentException("Unsupported type: ${T::class.simpleName}")
                }
            }
            value as T

        }.first()
    }


    inline fun <reified T> getPreferencesKey(keyName: String): Preferences.Key<*> {
        return when (T::class) {
            Int::class -> intPreferencesKey(keyName)
            Boolean::class -> booleanPreferencesKey(keyName)
            String::class -> stringPreferencesKey(keyName)
            Float::class -> floatPreferencesKey(keyName)
            Long::class -> longPreferencesKey(keyName)
            else -> {
                throw IllegalArgumentException("Unsupported type: ${T::class}")
            }
        }
    }

    suspend fun clearAll(context: Context) {
        withContext(Dispatchers.IO) {
            context.datastore.edit {
                it.clear()
            }
        }
    }
}