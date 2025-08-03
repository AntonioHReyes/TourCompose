package com.tonyakitori.apps.tourcompose.material3

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Rect
import com.tonyakitori.apps.tourcompose.TourCompose
import com.tonyakitori.apps.tourcompose.settings.TourComposePropertiesI
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.BubbleContentSettings
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultDialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultSpotlightColors

/**
 * Material3 wrapper for TourCompose that provides Material Theme integration out of the box.
 *
 * This composable automatically applies Material3 color scheme to the tour components,
 * making it easy to integrate with Material3 apps without manually configuring colors.
 *
 * @param componentRectArea The rectangular area of the component to highlight
 * @param bubbleContentSettings Settings for the bubble content
 * @param tourComposeProperties Optional custom properties, if not provided uses Material3 defaults
 */
@Composable
fun TourComposeMaterial3(
    componentRectArea: Rect?,
    bubbleContentSettings: BubbleContentSettings?,
    tourComposeProperties: TourComposeMaterial3Properties? = null
) {
    val materialTourProperties =
        tourComposeProperties ?: TourComposeMaterial3Properties.getDefaultInstance()

    TourCompose(
        tourComposeProperties = materialTourProperties,
        componentRectArea = componentRectArea,
        bubbleContentSettings = bubbleContentSettings
    )
}

@Immutable
data class TourComposeMaterial3Properties(
    override val spotlightColors: DefaultSpotlightColors,
    override val dialogBubbleColors: DefaultDialogBubbleColors
) : TourComposePropertiesI {
    companion object {
        @Composable
        fun getDefaultInstance(): TourComposeMaterial3Properties = TourComposeMaterial3Properties(
            spotlightColors = DefaultSpotlightColors(
                overlayBackgroundColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.8f),
                overlayBorderColor = MaterialTheme.colorScheme.primary
            ),
            dialogBubbleColors = DefaultDialogBubbleColors(
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}