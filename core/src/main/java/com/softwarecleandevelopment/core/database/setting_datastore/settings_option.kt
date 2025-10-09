package com.softwarecleandevelopment.core.database.setting_datastore


sealed class SettingsOption(val name: String) {
    data class Notification(val isEnabled: Boolean) : SettingsOption("notification")
    data class Currency(val code: String) : SettingsOption("currency")
    data class Language(val language: String) : SettingsOption("language")
}
