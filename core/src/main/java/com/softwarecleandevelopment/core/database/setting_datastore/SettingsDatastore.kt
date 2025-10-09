package com.softwarecleandevelopment.core.database.setting_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.softwarecleandevelopment.core.common.model.Settings
import com.softwarecleandevelopment.core.di.modules.SettingsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class SettingsDatastore @Inject constructor(
    @SettingsDataStore private val dataStore: DataStore<Preferences>,
) {
    private val isNotificationEnabled = booleanPreferencesKey("is_notification_enabled")
    private fun keyFor(name: Any) = stringPreferencesKey("${name}_Setting")

    /**
     * Exposes a Flow of the application's settings model.
     * This is what the Repository/Use Case will collect from.
     */
    val settingsFlow: Flow<Settings> = dataStore.data.catch { exception ->
        // Handle I/O errors and emit an empty Preferences object if needed
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        // Map the Preferences to your Domain/Data Model
        Settings(
            isNotificationsEnabled = preferences[isNotificationEnabled] ?: false,
            preferredCurrency = preferences[keyFor("currency_setting")] ?: "USD",
            preferredLanguage = preferences[keyFor("language_setting")] ?: "EN"
        )
    }

    suspend fun updateSettings(option: SettingsOption) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            when (option) {
                is SettingsOption.Notification -> {
                    preferences[isNotificationEnabled] = option.isEnabled
                }

                is SettingsOption.Currency -> {
                    preferences[keyFor(option.name)] = option.code
                }

                is SettingsOption.Language -> {
                    preferences[keyFor(option.name)] = option.language
                }
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