package com.softwarecleandevelopment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.softwarecleandevelopment.cryptowallet.navigation.AppNavGraph
import com.softwarecleandevelopment.core.common.ui.theme.CryptoWalletTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoWalletTheme {
                AppNavGraph()
            }
        }
    }

}