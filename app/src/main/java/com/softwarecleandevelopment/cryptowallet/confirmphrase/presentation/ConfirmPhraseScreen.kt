package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.cryptowallet.R
import com.softwarecleandevelopment.cryptowallet.confirmphrase.domain.models.Derived
import com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.viewmodel.CreateWalletViewModel

@Composable
fun ConfirmPhraseScreen(
    confirmPhraseViewModel: ConfirmPhraseViewModel = hiltViewModel(),
    createWalletViewModel: CreateWalletViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onSuccess: () -> Unit = {},
    derived: Derived?,
) {
    val context = LocalContext.current
    val window = (context as? Activity)?.window
    LaunchedEffect(key1 = Unit) {
        createWalletViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    DisposableEffect(Unit) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
    Scaffold(
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
                text = stringResource(R.string.confirm_phrase_note), fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 0 until 6) {
                        WordBox(
                            index = i,
                            word = confirmPhraseViewModel.selectedWords.value[i],
                            onClick = { })
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 6 until 12) {
                        WordBox(
                            index = i,
                            word = confirmPhraseViewModel.selectedWords.value[i],
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
                items(confirmPhraseViewModel.shuffledWords.value.size) { shuffledIndex ->
                    val word = confirmPhraseViewModel.shuffledWords.value[shuffledIndex]
                    if (word.isNotEmpty()) {
                        val isWrong = confirmPhraseViewModel.wrongItems.value[shuffledIndex]
                        val shakeOffset = shake(enabled = isWrong)
                        Box(
                            modifier = Modifier
                                .offset(x = shakeOffset.value.dp * 2) // Apply the animated offset
                                .background(
                                    if (isWrong) Color.Red else Color(
                                        0xFF1976D2
                                    ), RoundedCornerShape(6.dp)
                                )
                                .clickable {
                                    confirmPhraseViewModel.onWordClick(
                                        word, shuffledIndex
                                    )
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
                onClick = {
                    if (derived != null) {
                        createWalletViewModel.createWallet(derived = derived)
                        onSuccess()
                    }
                },
                enabled = confirmPhraseViewModel.isAllCorrect,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (confirmPhraseViewModel.isAllCorrect) MaterialTheme.colorScheme.primary else Color.DarkGray
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
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
    )
}

@Composable
fun WordBox(index: Int, word: String, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(6.dp))
            .clickable { onClick(index) }, contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "${index + 1}.$word",
            modifier = Modifier.padding(start = 10.dp),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun shake(enabled: Boolean, onAnimationEnd: () -> Unit = {}): State<Float> {
    val shake = remember { Animatable(0f) }
    LaunchedEffect(enabled) {
        if (enabled) {
            for (i in 0..10) {
                shake.animateTo(
                    targetValue = if (i % 2 == 0) 1f else -1f,
                    animationSpec = tween(durationMillis = 50, easing = LinearEasing)
                )
            }
            shake.animateTo(0f)
            onAnimationEnd()
        }
    }
    return shake.asState()
}
