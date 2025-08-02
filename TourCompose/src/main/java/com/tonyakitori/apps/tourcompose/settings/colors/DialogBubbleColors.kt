package com.tonyakitori.apps.tourcompose.settings.colors

import androidx.compose.material3.MaterialTheme
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
            backgroundColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun defaultDialogBubbleColors(): DialogBubbleColors = DefaultDialogBubbleColors(
    backgroundColor = MaterialTheme.colorScheme.surface
)