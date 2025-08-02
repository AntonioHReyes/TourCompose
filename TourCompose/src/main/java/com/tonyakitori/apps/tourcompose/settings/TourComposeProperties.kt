package com.tonyakitori.apps.tourcompose.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultDialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultSpotlightColors
import com.tonyakitori.apps.tourcompose.settings.colors.DialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.SpotlightColors

@Immutable
object TourComposeProperties {

    var spotlightColors: SpotlightColors = DefaultSpotlightColors()
        private set

    var dialogBubbleColors: DialogBubbleColors = DefaultDialogBubbleColors()
        private set

    @Composable
    fun copy(
        spotlightColors: SpotlightColors = DefaultSpotlightColors(),
        dialogBubbleColors: DialogBubbleColors = DefaultDialogBubbleColors()
    ): TourComposeProperties = TourComposeProperties.apply {
        this@TourComposeProperties.spotlightColors = spotlightColors
        this@TourComposeProperties.dialogBubbleColors = dialogBubbleColors
    }

}