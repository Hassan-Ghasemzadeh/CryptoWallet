package com.softwarecleandevelopment.cryptowallet.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.cryptowallet.presentation.viewmodel.WalletViewModel


@Composable
fun WalletScreen(innerPadding: PaddingValues, viewModel: WalletViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            "Mnemonic:",
            fontSize = 18.sp,
        )
        Text(
            viewModel.mnemonic.value,
            fontSize = 16.sp,
        )
        Text(
            "Address:",
            fontSize = 18.sp,
        )
        Text(
            viewModel.address.value,
            fontSize = 16.sp,
        )
        Button(
            onClick = {
                viewModel.generateWallet()
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.85f),
        ) {
            Text(
                "Generate Wallet",
                fontSize = 18.sp,
            )
        }
    }
}