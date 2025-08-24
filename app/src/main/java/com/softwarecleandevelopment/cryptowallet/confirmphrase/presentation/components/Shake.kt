package com.softwarecleandevelopment.cryptowallet.confirmphrase.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember


@Composable
fun shake(enabled: Boolean, onAnimationEnd: () -> Unit = {}): State<Float> {
    val shake = remember { Animatable(0f) }
    LaunchedEffect(enabled) {
        if (enabled) {
            for (i in 0..10) {
                shake.animateTo(
                    targetValue = if (i % 2 == 0) 1f else -1f,
                    animationSpec = tween(durationMillis = 50, easing = LinearEasing)
                )
            }
            shake.animateTo(0f)
            onAnimationEnd()
        }
    }
    return shake.asState()
}
