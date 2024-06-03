package com.tonyakitori.apps.tourcompose.settings.bubbleContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Stable
interface BubbleContentSettings {
    val modifier: Modifier
    val title: String
    val description: String

    //actions
    val onDismiss: () -> Unit

    @Composable
    fun DrawContent(
        modifier: Modifier
    )
}