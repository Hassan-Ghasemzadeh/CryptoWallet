package com.softwarecleandevelopment.feature.setting.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SettingItem(
    val title: String,
    val icon: ImageVector,
    val iconTint: Color,
    val subtitle: String? = null,
    val type: SettingType = SettingType.DEFAULT,
    val isEnabled: Boolean = false,
)

enum class SettingType {
    DEFAULT, SWITCH,
}
