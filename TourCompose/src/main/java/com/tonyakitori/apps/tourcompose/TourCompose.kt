package com.tonyakitori.apps.tourcompose

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
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
    tourComposeProperties: TourComposeProperties = TourComposeProperties.getDefaultInstance(),
    componentRectArea: Rect?,
    bubbleContentSettings: BubbleContentSettings?
) {

    if (componentRectArea == null){
        Log.e("TourCompose", "componentRectArea is null.")
        return
    }

    if (bubbleContentSettings == null) {
        Log.e("TourCompose", "bubbleContentSettings is null.")
        return
    }

    val density = LocalDensity.current
    var contentSize by remember { mutableStateOf(Size.Zero) }
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels
    val spotlightCenterY = componentRectArea.center.y
    val dialogContentDescription = stringResource(R.string.tour_compose_content_description)

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

    val finalComponentRect = run {
        val safeDrawingPaddingValues = WindowInsets.safeDrawing.asPaddingValues()
        val topPadding = with(LocalDensity.current) { safeDrawingPaddingValues.calculateTopPadding().toPx() }

        if (topPadding > 0) {
            Rect(
                left = componentRectArea.left,
                top = componentRectArea.top.minus(topPadding),
                right = componentRectArea.right,
                bottom = componentRectArea.bottom.minus(topPadding)
            )
        } else {
            componentRectArea
        }
    }

    val bubblePositionModifier = with(density) {
        if (spotlightCenterY < screenHeight / 2) {
            Modifier.offset(
                y = (finalComponentRect.bottom + BUBBLE_OFFSET_Y_BOTTOM.dp.toPx()).toDp()
            )
        } else {
            Modifier.offset(
                y = (finalComponentRect.top - (contentSize.height + BUBBLE_OFFSET_Y_TOP.dp.toPx())).toDp()
            )
        }
    }

    Popup(
        properties = PopupProperties(
            focusable = true,
        ),
        popupPositionProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: androidx.compose.ui.unit.LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                return IntOffset(
                    x = 0,
                    y = screenHeight - popupContentSize.height
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics {
                    testTagsAsResourceId = true
                    contentDescription = dialogContentDescription
                }
                .focusable(true)
        ) {
            OverlaySpotlight(
                modifier = Modifier,
                componentSelectedRect = finalComponentRect,
                isOverflow = isComponentOverflowing,
                colors = tourComposeProperties.spotlightColors
            )

            bubbleContentSettings.let {
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
}