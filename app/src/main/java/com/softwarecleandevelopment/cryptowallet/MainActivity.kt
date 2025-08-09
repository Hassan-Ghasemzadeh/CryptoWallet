package com.softwarecleandevelopment.cryptowallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.softwarecleandevelopment.cryptowallet.presentation.WalletScreen
import com.softwarecleandevelopment.cryptowallet.ui.theme.CryptoWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoWalletTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WalletScreen(innerPadding)
                }
            }
        }
    }
}