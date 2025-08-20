package com.softwarecleandevelopment.cryptowallet.useragreement.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.cryptowallet.R
import com.softwarecleandevelopment.core.common.ui.theme.CryptoWalletTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAgreementScreen(
    onContinueClicked: () -> Unit = {},
) {
    CryptoWalletTheme {
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            topBar = { UserAgreementAppBar() },
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAgreementAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "New Wallet",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }, navigationIcon = {
            IconButton(onClick = { /* Handle back action */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

/**
 * displaying an information card.
 * @param icon The icon to display on the card.
 * @param title The title of the information card.
 * @param description The detailed description for the card.
 */
@Composable
fun InfoCard(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * the "Continue" button at the bottom of the screen.
 * @param onClick Lambda to be invoked when the button is clicked.
 */
@Composable
fun ContinueButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = stringResource(R.string.recovery_phrase_btn),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewWalletScreen() {
    UserAgreementScreen()
}