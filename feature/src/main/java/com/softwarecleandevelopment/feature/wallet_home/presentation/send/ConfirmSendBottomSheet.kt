package com.softwarecleandevelopment.feature.wallet_home.presentation.send

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softwarecleandevelopment.crypto_chains.ethereum.domain.models.SendTokenEvent
import com.softwarecleandevelopment.feature.wallet_home.presentation.viewmodels.confirm_send.SendViewModel
import java.math.BigDecimal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmSendBottomSheet(
    viewModel: SendViewModel = hiltViewModel(),
    toAddress: String,
    tokenSymbol: String = "ETH",
    tokenAmount: String,
    tokenContractAddress: String? = null,
    onClose: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var privateKey by remember { mutableStateOf(viewModel.privateKey.value) }
    var toAddress by remember { mutableStateOf(toAddress) }
    var amount by remember { mutableStateOf(tokenAmount) }

    LaunchedEffect(Unit) {
        // estimate fee on open
        viewModel.estimateFee(toAddress, tokenSymbol)
        viewModel.getPrivateKey()
    }

    ModalBottomSheet(onDismissRequest = onClose, sheetState = sheetState) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Confirm send",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                    )
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onClose() })
                }

                Spacer(Modifier.height(12.dp))

                // Top card showing amount & token
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("$amount $tokenSymbol")
                    }
                }

                Spacer(Modifier.height(12.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("To")
                        Text(toAddress, fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Amount")
                        Text(amount, fontSize = 12.sp)
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Network fee info
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Network fee")
                        Spacer(Modifier.height(4.dp))
                        if (state.isLoading) Text("Estimating...") else Text(
                            state.networkFeeUsd ?: "—"
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Warnings
                if (state.error != null) {
                    Text(
                        state.error ?: "", color = Color.Red, modifier = Modifier.padding(4.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        // validation
                        if (privateKey.isBlank()) {
                            Toast.makeText(context, "Private key required", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }
                        if (toAddress.isBlank()) {
                            Toast.makeText(context, "To address required", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }
                        val amountBD = try {
                            BigDecimal(amount)
                        } catch (e: Exception) {
                            BigDecimal.ZERO
                        }
                        viewModel.send(
                            SendTokenEvent(
                                privateKey = privateKey,
                                toAddress = toAddress,
                                amountHuman = amountBD,
                                tokenContractAddress = tokenContractAddress
                            )
                        )
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp), enabled = !state.isLoading
                ) {
                    if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    else
                        Text(
                            "Confirm"
                        )
                }

                Spacer(Modifier.height(8.dp))

                // Show success hash
                state.successTransactionHash?.let { hash ->
                    Text(
                        "Sent — tx: $hash",
                        fontSize = 12.sp,
                        modifier = Modifier.clickable {},
                    )
                }
            }
        }
    }

}
