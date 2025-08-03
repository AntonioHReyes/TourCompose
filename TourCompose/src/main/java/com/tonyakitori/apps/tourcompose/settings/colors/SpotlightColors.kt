package com.tonyakitori.apps.tourcompose.settings.colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

interface SpotlightColors {
    val overlayBackgroundColor: Color
    val overlayBorderColor: Color
}

@Immutable
data class DefaultSpotlightColors(
    override val overlayBackgroundColor: Color,
    override val overlayBorderColor: Color
) : SpotlightColors {
    companion object {
        @Composable
        fun getDefaultInstance(): DefaultSpotlightColors = DefaultSpotlightColors(
            overlayBackgroundColor = Color.Gray.copy(alpha = 0.8f),
            overlayBorderColor = Color.Green
        )
    }
}

@Composable
fun defaultSpotlightColors(): SpotlightColors = DefaultSpotlightColors(
    overlayBackgroundColor = Color.Gray.copy(alpha = 0.8f),
    overlayBorderColor = Color.Green
)