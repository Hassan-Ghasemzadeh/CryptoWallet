package com.softwarecleandevelopment.feature.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softwarecleandevelopment.crypto_chains.crypto_info.data.model.Transaction

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium // Use rounded corners
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Row for Hash and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Transaction Hash
                Text(
                    text = "Hash: ${transaction.hash.take(8)}...",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                // Confirmation Status (e.g., Confirmed date or Pending)
                val statusText = transaction.confirmed ?: "Pending"
                val statusColor =
                    if (statusText != "Pending") Color(0xFF4CAF50) else Color(0xFFFF9800) // Green for Confirmed, Orange for Pending
                Text(
                    text = statusText,
                    color = statusColor,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.1f), MaterialTheme.shapes.small)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row for Total and Fees
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Total Value
                TransactionDetail(label = "Total:", value = "${transaction.total ?: 0} Units")

                // Fees
                TransactionDetail(label = "Fees:", value = "${transaction.fees ?: 0} Units")
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Confirmations
            TransactionDetail(label = "Confirmations:", value = "${transaction.confirmation ?: 0}")
        }
    }
}

// Helper Composable for consistent detail display
@Composable
private fun TransactionDetail(label: String, value: String) {
    Row {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionItem() {
    val sampleTransaction = Transaction(
        hash = "a1b2c3d4e5f67890abcdef1234567890",
        confirmation = 15,
        confirmed = "2023-10-09T10:00:00Z",
        total = 550000000L,
        fees = 12000L,
        inputs = listOf(),
        outputs = listOf()
    )
    MaterialTheme {
        Column {
            TransactionItem(transaction = sampleTransaction)
            TransactionItem(
                transaction = sampleTransaction.copy(
                    confirmed = null,
                    hash = "b9a8b7c6d5e4f3g2h1i0j9k8l7m6n5o4",
                    confirmation = 0
                )
            )
        }
    }
}