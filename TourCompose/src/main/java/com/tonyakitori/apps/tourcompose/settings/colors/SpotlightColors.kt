package com.tonyakitori.apps.tourcompose.settings.colors

import androidx.compose.material3.MaterialTheme
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
            overlayBackgroundColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.8f),
            overlayBorderColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun defaultSpotlightColors(): SpotlightColors = DefaultSpotlightColors(
    overlayBackgroundColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.8f),
    overlayBorderColor = MaterialTheme.colorScheme.primary
)