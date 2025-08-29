package com.softwarecleandevelopment.feature.wallets.presentation

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.feature.wallets.presentation.components.ShowPhraseAppBar
import com.softwarecleandevelopment.feature.wallets.presentation.components.WarningBox
import com.softwarecleandevelopment.feature.wallets.presentation.components.WordBox
import com.softwarecleandevelopment.feature.wallets.presentation.viewmodels.SecretPhraseViewModel
import com.softwarecleandevelopment.feature.R

@Composable
fun SecretPhraseScreen(
    viewModel: SecretPhraseViewModel = hiltViewModel(),
    mnemonic: String?,
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val window = (context as? Activity)?.window
    val words = viewModel.getMnemonic(mnemonic)

    DisposableEffect(Unit) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
    Scaffold(
        topBar = { ShowPhraseAppBar(onNavigateBack) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WarningBox()
            Spacer(modifier = Modifier.height(24.dp))
            if (words.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in 0 until 6) {
                            WordBox(
                                index = i,
                                word = words[i],
                                onClick = { })
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in 6 until 12) {
                            WordBox(
                                index = i,
                                word = words[i],
                                onClick = { })
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        stringResource(R.string.secretphrase_emptylist),
                        modifier = Modifier.align(Alignment.Center)
                    )

                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { viewModel.copyToClipboard(context) }) {
                Text(
                    text = stringResource(R.string.secretphrase_copy_brn),
                    color = MaterialTheme.colorScheme.primary, // Using primary color for consistency
                    fontSize = 18.sp,
                )
            }
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}