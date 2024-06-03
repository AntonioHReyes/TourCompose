package com.tonyakitori.apps.tourcompose.settings.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

interface DialogBubbleColors {
    val backgroundColor: Color
}

@Immutable
class DefaultDialogBubbleColors(
    override val backgroundColor: Color = Color.White
) : DialogBubbleColors {

    fun copy(
        backgroundColor: Color = this.backgroundColor
    ): DialogBubbleColors = DefaultDialogBubbleColors(
        backgroundColor = backgroundColor
    )
}