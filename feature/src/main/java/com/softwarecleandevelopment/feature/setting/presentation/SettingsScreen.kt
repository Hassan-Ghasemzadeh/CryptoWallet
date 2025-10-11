package com.softwarecleandevelopment.feature.setting.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.HorizontalDivider
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.setting.presentation.components.SettingRow
import com.softwarecleandevelopment.feature.setting.presentation.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings = viewModel.settingsList.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(settings.value) { item ->
            SettingRow(item) { viewModel.onSettingClick(it) }
            HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color(0xFFF1F1F1))
        }
    }
}
