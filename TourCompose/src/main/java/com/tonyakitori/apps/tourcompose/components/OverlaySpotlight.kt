package com.tonyakitori.apps.tourcompose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.ALPHA
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.SPOTLIGHT_OVERFLOW_RECT_BORDER_PADDING
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.SPOTLIGHT_OVERFLOW_RECT_BORDER_WIDTH
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.SPOTLIGHT_OVERFLOW_RECT_PADDING
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.SPOTLIGHT_RECT_BORDER_PADDING
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.SPOTLIGHT_RECT_BORDER_WIDTH
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.SPOTLIGHT_RECT_CORNER_RADIUS
import com.tonyakitori.apps.tourcompose.components.GuidedTourOverlaySpotlightProperties.SPOTLIGHT_RECT_PADDING
import com.tonyakitori.apps.tourcompose.settings.colors.SpotlightColors

object GuidedTourOverlaySpotlightProperties {
    const val ALPHA = 0.78f

    const val SPOTLIGHT_RECT_CORNER_RADIUS = 16f
    const val SPOTLIGHT_RECT_PADDING = 8f
    const val SPOTLIGHT_RECT_BORDER_PADDING = 6.55f
    const val SPOTLIGHT_RECT_BORDER_WIDTH = 4

    const val SPOTLIGHT_OVERFLOW_RECT_PADDING = 2.8f
    const val SPOTLIGHT_OVERFLOW_RECT_BORDER_PADDING = 2f
    const val SPOTLIGHT_OVERFLOW_RECT_BORDER_WIDTH = 2
}

/**
 * Overlay that shows a spotlight on the selected component.
 *
 * @param modifier Modifier to be applied to the OverlaySpotlight.
 * @param isOverflow If the component is overflowing the bubble.
 * @param componentSelectedRect Rect of the selected component.
 * @param colors Colors of the spotlight.
 */
@Composable
internal fun OverlaySpotlight(
    modifier: Modifier = Modifier,
    isOverflow: Boolean = false,
    componentSelectedRect: Rect,
    colors: SpotlightColors
) {

    val backgroundColor = colors.overlayBackgroundColor
    val strokeRectColor = colors.overlayBorderColor

    Canvas(
        modifier = modifier
            .fillMaxSize(),
        onDraw = {
            val spotlightPath = Path().apply {

                val rectWithPadding = Rect(
                    left = componentSelectedRect.left - if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_PADDING.dp.toPx()
                    },
                    top = componentSelectedRect.top - if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_PADDING.dp.toPx()
                    },
                    right = componentSelectedRect.right + if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_PADDING.dp.toPx()
                    },
                    bottom = componentSelectedRect.bottom + if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_PADDING.dp.toPx()
                    },
                )

                val rectWithPaddingForBorders = Rect(
                    left = componentSelectedRect.left - if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_BORDER_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_BORDER_PADDING.dp.toPx()
                    },
                    top = componentSelectedRect.top - if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_BORDER_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_BORDER_PADDING.dp.toPx()
                    },
                    right = componentSelectedRect.right + if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_BORDER_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_BORDER_PADDING.dp.toPx()
                    },
                    bottom = componentSelectedRect.bottom + if (isOverflow) {
                        SPOTLIGHT_OVERFLOW_RECT_BORDER_PADDING.dp.toPx()
                    } else {
                        SPOTLIGHT_RECT_BORDER_PADDING.dp.toPx()
                    },
                )

                val cornerRadius = CornerRadius(
                    SPOTLIGHT_RECT_CORNER_RADIUS.dp.toPx(),
                    SPOTLIGHT_RECT_CORNER_RADIUS.dp.toPx()
                )

                // Draw the rectangle with only borders
                drawRoundRect(
                    color = strokeRectColor,
                    topLeft = rectWithPaddingForBorders.topLeft,
                    size = Size(
                        width = rectWithPaddingForBorders.width,
                        height = rectWithPaddingForBorders.height
                    ),
                    cornerRadius = cornerRadius,
                    style = Stroke(
                        width = if (isOverflow) {
                            SPOTLIGHT_OVERFLOW_RECT_BORDER_WIDTH.dp.toPx()
                        } else {
                            SPOTLIGHT_RECT_BORDER_WIDTH.dp.toPx()
                        }
                    )
                )

                // Draw the rectangle that covers the rectangle with borders and the component, this is the clip area
                addRoundRect(
                    roundRect = RoundRect(
                        rect = rectWithPadding,
                        cornerRadius = cornerRadius
                    )
                )
            }

            clipPath(
                path = spotlightPath,
                clipOp = ClipOp.Difference
            ) {
                drawRect(
                    brush = SolidColor(backgroundColor.copy(alpha = ALPHA))
                )
            }
        }
    )
}
