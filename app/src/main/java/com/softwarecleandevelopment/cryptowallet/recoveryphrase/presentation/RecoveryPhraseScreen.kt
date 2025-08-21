package com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.viewmodel.RecoveryPhraseViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecoveryPhraseScreen(
    viewModel: RecoveryPhraseViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onContinueClicked: (mnemonic: String) -> Unit = {},
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.Companion.fillMaxSize(),
        topBar = { RecoveryPhraseAppBar(onNavigateBack) },
        containerColor = MaterialTheme.colorScheme.onPrimary
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
                    onContinueClicked(viewModel.mnemonic.value)
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

@Composable
fun RecoveryWordChip(index: Int, word: String) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        color = Color.White,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$index", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = word, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryPhraseAppBar(
    onNavigateBack: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = "", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
            )
        }, navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary  // App bar background color
        )
    )
}

@Composable
fun WarningBox() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFE0F2F7) // Light blue background
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info",
                tint = Color(0xFF2196F3), // Blue icon color
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.phrase_alert), fontSize = 14.sp, color = Color.Black
            )
        }
    }
}