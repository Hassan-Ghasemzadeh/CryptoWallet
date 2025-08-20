package com.softwarecleandevelopment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.softwarecleandevelopment.cryptowallet.navigation.CryptoWalletNavGraph
import com.softwarecleandevelopment.core.common.ui.theme.CryptoWalletTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the observer
        enableEdgeToEdge()
        setContent {
            CryptoWalletTheme {
                CryptoWalletNavGraph()
            }
        }
    }

}