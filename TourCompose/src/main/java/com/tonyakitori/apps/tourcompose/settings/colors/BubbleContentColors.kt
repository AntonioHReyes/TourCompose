package com.tonyakitori.apps.tourcompose.settings.colors

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

interface BubbleContentColors {
    val titleTextColor: Color
    val descriptionTextColor: Color
    val iconTintColor: Color
    val dividerColor: Color
}

@Immutable
class DefaultBubbleContentColors(
    override val titleTextColor: Color,
    override val descriptionTextColor: Color,
    override val iconTintColor: Color,
    override val dividerColor: Color
) : BubbleContentColors {

    fun copy(
        titleTextColor: Color = this.titleTextColor,
        descriptionTextColor: Color = this.descriptionTextColor,
        iconTintColor: Color = this.iconTintColor,
        dividerColor: Color = this.dividerColor
    ): BubbleContentColors = DefaultBubbleContentColors(
        titleTextColor = titleTextColor,
        descriptionTextColor = descriptionTextColor,
        iconTintColor = iconTintColor,
        dividerColor = dividerColor
    )
}

@Composable
fun defaultBubbleContentColors(): DefaultBubbleContentColors = DefaultBubbleContentColors(
    titleTextColor = MaterialTheme.colorScheme.onSurface,
    descriptionTextColor = MaterialTheme.colorScheme.onSurface,
    iconTintColor = MaterialTheme.colorScheme.onSurface,
    dividerColor = MaterialTheme.colorScheme.outline
)