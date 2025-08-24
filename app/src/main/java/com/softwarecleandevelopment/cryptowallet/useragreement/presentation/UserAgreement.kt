package com.softwarecleandevelopment.cryptowallet.useragreement.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.cryptowallet.R
import com.softwarecleandevelopment.core.common.ui.theme.CryptoWalletTheme
import com.softwarecleandevelopment.cryptowallet.useragreement.components.ContinueButton
import com.softwarecleandevelopment.cryptowallet.useragreement.components.InfoCard
import com.softwarecleandevelopment.cryptowallet.useragreement.components.UserAgreementAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAgreementScreen(
    onContinueClicked: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    CryptoWalletTheme {
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            topBar = { UserAgreementAppBar(onNavigateBack) },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.terms_note),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp, lineHeight = 24.sp, textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                InfoCard(
                    icon = Icons.Default.Create,
                    title = stringResource(R.string.term_one_title),
                    description = stringResource(R.string.term_one_desc),
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoCard(
                    icon = Icons.Default.Warning,
                    title = stringResource(R.string.term_two_title),
                    description = stringResource(R.string.term_two_desc),
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoCard(
                    icon = Icons.Default.Lock,
                    title = stringResource(R.string.term_three_title),
                    description = stringResource(R.string.term_three_desc),
                )
                Spacer(modifier = Modifier.weight(1f))

                ContinueButton(onClick = onContinueClicked)
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewNewWalletScreen() {
    UserAgreementScreen()
}