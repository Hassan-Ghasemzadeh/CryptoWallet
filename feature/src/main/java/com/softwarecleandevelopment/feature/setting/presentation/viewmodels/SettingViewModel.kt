package com.softwarecleandevelopment.feature.setting.presentation.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.softwarecleandevelopment.feature.setting.domain.model.SettingItem
import com.softwarecleandevelopment.feature.setting.domain.model.SettingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _settingsList = MutableStateFlow(
        listOf(
            SettingItem("Wallets", Icons.Default.Wallet, Color(0xFF2962FF)),
            SettingItem("Security", Icons.Default.Security, Color.Black),
            SettingItem(
                "Notifications",
                Icons.Default.Notifications,
                Color(0xFFF44336),
                type = SettingType.SWITCH,
                isEnabled = false
            ),
            SettingItem("Dark Mode", Icons.Default.DarkMode, Color(0xFF9C27B0), subtitle = "Light"),

            SettingItem("Price Alerts", Icons.Default.PriceChange, Color(0xFF4CAF50)),
            SettingItem("Currency", Icons.Default.AttachMoney, Color(0xFFFF9800), subtitle = "USD"),
            SettingItem(
                "Language", Icons.Default.Language, Color(0xFF03A9F4), subtitle = "English"
            ),
            SettingItem("Networks", Icons.Default.Wifi, Color(0xFF673AB7)),
            SettingItem("WalletConnect", Icons.Default.Link, Color(0xFF2196F3)),
            SettingItem("Telegram", Icons.AutoMirrored.Filled.Send, Color(0xFF2196F3)),
            SettingItem("YouTube", Icons.Default.PlayCircle, Color(0xFFF32121)),
            SettingItem("Help Center", Icons.AutoMirrored.Filled.Help, Color(0xFFF37521)),
            SettingItem("Support", Icons.Default.Support, Color(0xFF2196F3)),
            SettingItem("About Us", Icons.Default.Info, Color(0xFF022D77))
        )
    )
    val settingsList = _settingsList.asStateFlow()

    fun onSettingClick(item: SettingItem) {
        if (item.type == SettingType.SWITCH) {
            _settingsList.value = _settingsList.value.map {
                if (it.title == item.title) item else it
            }
        }
    }
}