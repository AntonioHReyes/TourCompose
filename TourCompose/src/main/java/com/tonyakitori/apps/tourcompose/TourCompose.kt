package com.tonyakitori.apps.tourcompose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.tonyakitori.apps.tourcompose.TourComposeConstants.BUBBLE_OFFSET_Y_BOTTOM
import com.tonyakitori.apps.tourcompose.TourComposeConstants.BUBBLE_OFFSET_Y_TOP
import com.tonyakitori.apps.tourcompose.TourComposeConstants.HORIZONTAL_PADDING
import com.tonyakitori.apps.tourcompose.TourComposeConstants.RESTRICTION_TAIL_OFFSET
import com.tonyakitori.apps.tourcompose.components.DialogBubblePosition
import com.tonyakitori.apps.tourcompose.components.DialogBubbleSkeleton
import com.tonyakitori.apps.tourcompose.components.OverlaySpotlight
import com.tonyakitori.apps.tourcompose.settings.TourComposeProperties
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.BubbleContentSettings

internal object TourComposeConstants {
    const val BUBBLE_OFFSET_Y_TOP = 10f
    const val BUBBLE_OFFSET_Y_BOTTOM = 10f

    val HORIZONTAL_PADDING = 20.dp
    val RESTRICTION_TAIL_OFFSET = 26.dp
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TourCompose(
    isInsideScaffold: Boolean = false,
    tourComposeProperties: TourComposeProperties = TourComposeProperties,
    componentSelectedLayoutCoordinates: LayoutCoordinates?,
    bubbleContentSettings: BubbleContentSettings?
) {
    if (componentSelectedLayoutCoordinates == null) {
        Log.w("TourCompose", "componentSelectedLayoutCoordinates is null.")
        return
    }

    if (componentSelectedLayoutCoordinates.isAttached.not()) {
        Log.w("TourCompose", "componentSelectedLayoutCoordinates is not attached.")
        return
    }

    if (bubbleContentSettings == null) {
        Log.e("TourCompose", "bubbleContentSettings is null.")
    }

    val density = LocalDensity.current
    var contentSize by remember { mutableStateOf(Size.Zero) }
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels
    val componentRectArea = if (isInsideScaffold) {
        componentSelectedLayoutCoordinates.boundsInParent()
    } else {
        componentSelectedLayoutCoordinates.boundsInRoot()
    }
    val spotlightCenterY = componentRectArea.center.y

    val bubblePositionModifier = with(density) {
        if (spotlightCenterY < screenHeight / 2) {
            Modifier.offset(
                y = (componentRectArea.bottom + BUBBLE_OFFSET_Y_BOTTOM.dp.toPx()).toDp()
            )
        } else {
            Modifier.offset(
                y = (componentRectArea.top - (contentSize.height + BUBBLE_OFFSET_Y_TOP.dp.toPx())).toDp()
            )
        }
    }

    val isComponentOverflowing = with(density) {
        componentRectArea.topCenter.x <= HORIZONTAL_PADDING.toPx() ||
                componentRectArea.topCenter.x >= screenWidth - HORIZONTAL_PADDING.toPx() * 2
    }

    val dialogTailOffset = with(density) {
        val tail = when {
            /**
             * Check if the component is overflowing the bubble width.
             * If the component is overflowing at the start of the screen
             * we apply a restriction to put the tail in the value of RESTRICTION_TAIL_OFFSET.
             */
            componentRectArea.topCenter.x <= HORIZONTAL_PADDING.toPx() -> {
                RESTRICTION_TAIL_OFFSET.toPx()
            }
            /**
             * Check if the component is overflowing the bubble width.
             * If the component is overflowing at the end of the screen we apply a restriction
             * to put the tail at the end of the bubble minus the value of RESTRICTION_TAIL_OFFSET.
             */
            componentRectArea.topCenter.x >= screenWidth - HORIZONTAL_PADDING.toPx() * 2 -> {
                screenWidth - HORIZONTAL_PADDING.toPx() * 2 - RESTRICTION_TAIL_OFFSET.toPx()
            }

            /**
             * If the component is not overflowing the bubble width we put the tail in the center
             * of the component that is selected.
             */
            else -> {
                componentRectArea.topCenter.x - HORIZONTAL_PADDING.toPx()
            }
        }

        tail
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true }
            .clickable(
                onClick = { /* Intercept clicks */ },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        OverlaySpotlight(
            modifier = Modifier,
            componentSelectedLayoutCoordinates = componentSelectedLayoutCoordinates,
            isOverflow = isComponentOverflowing,
            isInsideScaffold = isInsideScaffold,
            colors = tourComposeProperties.spotlightColors
        )

        bubbleContentSettings?.let {
            DialogBubbleSkeleton(
                modifier = Modifier
                    .onGloballyPositioned {
                        // update the size of the SpeechBubble when it is positioned in the screen
                        // to calculate the position of the SpeechBubble correctly.
                        contentSize = it.size.toSize()
                    }
                    .then(bubblePositionModifier)
                    .fillMaxWidth()
                    .padding(horizontal = HORIZONTAL_PADDING),
                dialogBubblePosition = if (spotlightCenterY < screenHeight / 2) {
                    DialogBubblePosition.BOTTOM
                } else {
                    DialogBubblePosition.TOP
                },
                dialogTailOffsetX = dialogTailOffset,
                dialogBubbleColors = tourComposeProperties.dialogBubbleColors
            ) {
                it.DrawContent(Modifier)
            }
        }
    }
}