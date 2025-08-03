package com.tonyakitori.apps.tourcompose.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.tonyakitori.apps.tourcompose.settings.colors.DialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.SpotlightColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultDialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultSpotlightColors

interface TourComposePropertiesI {
    val spotlightColors: SpotlightColors
    val dialogBubbleColors: DialogBubbleColors
}

@Immutable
data class TourComposeProperties(
    override val spotlightColors: SpotlightColors,
    override val dialogBubbleColors: DialogBubbleColors
) : TourComposePropertiesI {
    companion object {
        @Composable
        fun getDefaultInstance(): TourComposeProperties = TourComposeProperties(
            spotlightColors = defaultSpotlightColors(),
            dialogBubbleColors = defaultDialogBubbleColors()
        )
    }
}