package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation

import com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.viewmodel.ConfirmPhraseViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.cryptowallet.R

@Composable
fun ConfirmPhraseScreen(
    viewModel: ConfirmPhraseViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        topBar = { ConfirmPhraseAppBar(onNavigateBack) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.confirm_phrase_note),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 0 until 6) {
                        WordBox(
                            index = i,
                            word = viewModel.selectedWords.value[i],
                            isWrong = false,
                            onClick = { })
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 6 until 12) {
                        WordBox(
                            index = i,
                            word = viewModel.selectedWords.value[i],
                            isWrong = false,
                            onClick = { })
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(80.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.shuffledWords.value.size) { shuffledIndex ->
                    val word = viewModel.shuffledWords.value[shuffledIndex]
                    if (word.isNotEmpty()) {
                        val isWrong = viewModel.wrongItems.value[shuffledIndex]
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isWrong) Color.Red else Color(0xFF1976D2),
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable {
                                    if (!isWrong)
                                        viewModel.onWordClick(word, shuffledIndex)
                                }
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = word, color = Color.White, fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onSuccess() },
                enabled = viewModel.isAllCorrect,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewModel.isAllCorrect) MaterialTheme.colorScheme.primary else Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Continue", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPhraseAppBar(
    onNavigateBack: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.confirm_phrase_title),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
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
fun WordBox(index: Int, word: String, isWrong: Boolean, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(6.dp))
            .clickable { onClick(index) }, contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = word.ifEmpty { "${index + 1}." }, modifier = Modifier.padding(start = 10.dp)
        )
    }
}