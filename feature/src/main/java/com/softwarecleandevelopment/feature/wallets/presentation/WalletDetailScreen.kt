package com.softwarecleandevelopment.feature.wallets.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallets.domain.models.UpdateWalletEvent
import com.softwarecleandevelopment.feature.wallets.presentation.viewmodels.WalletDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletDetailScreen(
    event: UpdateWalletEvent?,
    onNavigateBack: () -> Unit = {},
    onShowScreenPhrase: (phraseList: List<String>) -> Unit = {},
    viewModel: WalletDetailViewModel = hiltViewModel()
) {
    val focus = LocalFocusManager.current
    LaunchedEffect(Unit) {
        if (event != null) {
            viewModel.updateWalletName(
                UpdateWalletEvent(
                    name = event.name,
                    walletId = event.walletId,
                    mnemonic = event.mnemonic,
                )
            )
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Wallet") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.name.value,
                onValueChange = { name ->
                    if (event != null) {
                        viewModel.updateWalletName(
                            UpdateWalletEvent(
                                name = name,
                                walletId = event.walletId,
                                mnemonic = event.mnemonic,
                            )
                        )
                    }
                },
                label = {
                    Text("Name")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focus.clearFocus()
                    }
                ),
                shape = RoundedCornerShape(12.dp),
            )
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(
                Modifier
                    .fillMaxWidth(),
                DividerDefaults.Thickness,
                DividerDefaults.color
            )
            ListItem(
                headlineContent = {
                    Text("Show Secret Phrase", style = MaterialTheme.typography.labelLarge)
                },
                trailingContent = {
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .clickable {
                        onShowScreenPhrase(listOf())
                    }
                    .padding(horizontal = 4.dp)
            )
            HorizontalDivider(
                Modifier
                    .fillMaxWidth(),
                DividerDefaults.Thickness,
                DividerDefaults.color
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                )
            ) {
                Text("Delete")
            }
            Spacer(Modifier.weight(1f))
        }

    }
}
