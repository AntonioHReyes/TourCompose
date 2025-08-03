package com.tonyakitori.apps.tourcompose.settings.colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

interface DialogBubbleColors {
    val backgroundColor: Color
}

@Immutable
data class DefaultDialogBubbleColors(
    override val backgroundColor: Color
) : DialogBubbleColors {
    companion object {
        @Composable
        fun getDefaultInstance(): DefaultDialogBubbleColors = DefaultDialogBubbleColors(
            backgroundColor = Color.White
        )
    }
}

@Composable
fun defaultDialogBubbleColors(): DialogBubbleColors = DefaultDialogBubbleColors(
    backgroundColor = Color.White
)