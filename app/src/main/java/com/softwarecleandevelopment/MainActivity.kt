package com.softwarecleandevelopment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.softwarecleandevelopment.cryptowallet.recoveryphrase.presentation.RecoveryPhraseScreen
import com.softwarecleandevelopment.cryptowallet.ui.theme.CryptoWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoWalletTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    containerColor = Color.White,
                ) { innerPadding ->
                    RecoveryPhraseScreen(innerPadding)
                }
            }
        }
    }
}