package com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.cryptowallet.R
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.components.RecoveryPhraseAppBar
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.components.RecoveryWordChip
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.components.WarningBox
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.viewmodel.RecoveryPhraseViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecoveryPhraseScreen(
    viewModel: RecoveryPhraseViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onContinueClicked: (derived: Derived) -> Unit = {},
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.Companion.fillMaxSize(),
        topBar = { RecoveryPhraseAppBar(onNavigateBack) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.recovery_phrase_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


            Text(
                text = stringResource(R.string.phrase_note),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 4 // Adjust as needed to match the image's wrapping
            ) {
                viewModel.phraseList.value.forEachIndexed { index, word ->
                    RecoveryWordChip(index = index + 1, word = word)
                }
            }


            TextButton(onClick = {
                viewModel.copyToClipboard(
                    context = context,
                )
            }) {
                Text(
                    text = "Copy",
                    color = MaterialTheme.colorScheme.primary, // Using primary color for consistency
                    fontSize = 18.sp
                )
            }

            WarningBox()

            Button(
                onClick = {
                    onContinueClicked(viewModel.derived.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Continue", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
