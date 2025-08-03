package com.tonyakitori.apps.tourcompose.material3.colors

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.tonyakitori.apps.tourcompose.settings.colors.BubbleContentColors

/**
 * Material3 implementation of BubbleContentColors that automatically uses
 * colors from the current Material3 theme.
 */
@Immutable
class Material3BubbleContentColors(
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
    ): BubbleContentColors = Material3BubbleContentColors(
        titleTextColor = titleTextColor,
        descriptionTextColor = descriptionTextColor,
        iconTintColor = iconTintColor,
        dividerColor = dividerColor
    )
}

/**
 * Creates BubbleContentColors using the current Material3 theme.
 * This ensures consistent styling with your Material3 app.
 */
@Composable
fun material3BubbleContentColors(): Material3BubbleContentColors = Material3BubbleContentColors(
    titleTextColor = MaterialTheme.colorScheme.onSurface,
    descriptionTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
    iconTintColor = MaterialTheme.colorScheme.primary,
    dividerColor = MaterialTheme.colorScheme.outline
)