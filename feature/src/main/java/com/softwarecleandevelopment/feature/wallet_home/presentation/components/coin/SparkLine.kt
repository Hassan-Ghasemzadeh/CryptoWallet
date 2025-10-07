package com.softwarecleandevelopment.feature.wallet_home.presentation.components.coin

import java.util.Locale
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import kotlin.math.max

@Composable
fun Sparkline(
    points: List<Double>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(220.dp),
    lineColor: Color = Color(0xFF3399FF),
    labelColor: Color = Color(0xFF9AA0A6)
) {
    if (points.isEmpty()) {
        Box(modifier = modifier) { /* empty */ }
        return
    }

    // top-right and bottom-left labels like image
    val minVal = points.minOrNull() ?: 0.0
    val maxVal = points.maxOrNull() ?: 0.0
    val leftLabel = String.format(Locale.US, "$%,.2f", minVal)
    val rightLabel = String.format(Locale.US, "$%,.2f", maxVal)

    ColumnWithLabels(leftLabel, rightLabel, labelColor) {
        Canvas(modifier = modifier) {
            val w = size.width
            val h = size.height
            val n = points.size
            val minP = minVal
            val maxP = maxVal
            val range = if (maxP - minP == 0.0) 1.0 else (maxP - minP)

            val path = Path()
            for (i in points.indices) {
                val x = i * (w / max(1, n - 1))
                val normalized = (points[i] - minP) / range
                val y = h - (normalized * h).toFloat()
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }

            // draw shadow (thin) if needed
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )
        }
    }
}

@Composable
private fun ColumnWithLabels(
    leftLabel: String,
    rightLabel: String,
    labelColor: Color,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Column {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(leftLabel, style = TextStyle(color = labelColor, fontSize = 12.sp))
            Spacer(modifier = Modifier.weight(1f))
            Text(rightLabel, style = TextStyle(color = labelColor, fontSize = 12.sp))
            Spacer(modifier = Modifier.width(8.dp))
        }
        content()
    }
}