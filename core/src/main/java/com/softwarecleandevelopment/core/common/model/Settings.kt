package com.softwarecleandevelopment.core.common.model

data class Settings(
    val isNotificationsEnabled: Boolean,
    val preferredCurrency: String,
    val preferredLanguage: String
)