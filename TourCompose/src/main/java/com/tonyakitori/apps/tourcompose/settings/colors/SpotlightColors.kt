package com.tonyakitori.apps.tourcompose.settings.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

interface SpotlightColors {
    val overlayBackgroundColor: Color
    val overlayBorderColor: Color
}

@Immutable
class DefaultSpotlightColors(
    override val overlayBackgroundColor: Color = Color.Gray.copy(alpha = 0.5f),
    override val overlayBorderColor: Color = Color.Green
) : SpotlightColors {

    fun copy(
        overlayBackgroundColor: Color = this.overlayBackgroundColor,
        overlayBorderColor: Color = this.overlayBorderColor
    ): SpotlightColors = DefaultSpotlightColors(
        overlayBackgroundColor = overlayBackgroundColor,
        overlayBorderColor = overlayBorderColor
    )
}