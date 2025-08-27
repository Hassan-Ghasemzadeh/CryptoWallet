package com.softwarecleandevelopment.feature.wallets.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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