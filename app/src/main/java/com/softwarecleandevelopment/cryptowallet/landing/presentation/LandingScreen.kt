package com.softwarecleandevelopment.cryptowallet.landing.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwarecleandevelopment.cryptowallet.R

@Composable
fun LandingScreen(onCreateWalletClicked: () -> Unit = {}) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Coin Icon (replace with your own icon from drawable)
                Image(
                    painter = painterResource(id = R.drawable.ic_coin), // <-- replace with your coin icon
                    contentDescription = "Coin Icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Welcome Text
                Text(
                    text = stringResource(R.string.welcome_title),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Create New Wallet Button
                Button(
                    onClick = onCreateWalletClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.welcome_create_wallet_btn),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Import Existing Wallet Button
                Button(
                    onClick = { /* Navigate to import wallet */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.welcome_import_wallet_btn),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}