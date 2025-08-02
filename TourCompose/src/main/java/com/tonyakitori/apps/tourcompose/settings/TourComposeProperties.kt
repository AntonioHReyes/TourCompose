package com.tonyakitori.apps.tourcompose.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.tonyakitori.apps.tourcompose.settings.colors.DialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.SpotlightColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultDialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultSpotlightColors

@Immutable
data class TourComposeProperties(
    val spotlightColors: SpotlightColors,
    val dialogBubbleColors: DialogBubbleColors
) {
    companion object {
        @Composable
        fun getDefaultInstance(): TourComposeProperties = TourComposeProperties(
            spotlightColors = defaultSpotlightColors(),
            dialogBubbleColors = defaultDialogBubbleColors()
        )
    }
}