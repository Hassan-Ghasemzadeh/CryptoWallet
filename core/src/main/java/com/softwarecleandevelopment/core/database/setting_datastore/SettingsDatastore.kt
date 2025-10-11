package com.softwarecleandevelopment.core.database.setting_datastore

import android.content.Context
import com.softwarecleandevelopment.core.common.utils.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsDatastore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend fun getSetting(option: SettingsOption) = withContext(Dispatchers.IO) {
        when (option) {
            is SettingsOption.Currency -> DataStoreManager.get<String>(
                context,
                option.name,
                option.code
            )

            is SettingsOption.DarkMode -> DataStoreManager.get<Boolean>(
                context,
                option.name,
                option.isEnabled
            )

            is SettingsOption.Language -> DataStoreManager.get<String>(
                context,
                option.name,
                option.language
            )

            is SettingsOption.Notification -> DataStoreManager.get<Boolean>(
                context,
                option.name,
                option.isEnabled
            )
        }
    }

    suspend fun updateSettings(option: SettingsOption) = withContext(Dispatchers.IO) {
        when (option) {
            is SettingsOption.Notification -> {
                DataStoreManager.put<Boolean>(
                    context,
                    option.name,
                    option.isEnabled,
                )
            }

            is SettingsOption.Currency -> {
                DataStoreManager.put<String>(
                    context,
                    option.name,
                    option.code,
                )
            }

            is SettingsOption.Language -> {
                DataStoreManager.put<String>(
                    context,
                    option.name,
                    option.language,
                )
            }

            is SettingsOption.DarkMode -> {
                DataStoreManager.put<Boolean>(
                    context,
                    option.name,
                    option.isEnabled,
                )
            }
        }
    }


    suspend fun clearAll() {
        DataStoreManager.clearAll(context = context)
    }
}