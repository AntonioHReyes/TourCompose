package com.tonyakitori.apps.tourcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tonyakitori.apps.tourcompose.components.DialogBubbleProperties.BUBBLE_RADIUS
import com.tonyakitori.apps.tourcompose.components.DialogBubbleProperties.BUBBLE_WRAPPER_Z_INDEX
import com.tonyakitori.apps.tourcompose.components.DialogBubbleProperties.DIALOG_TAIL_OFFSET_X
import com.tonyakitori.apps.tourcompose.components.DialogBubbleProperties.TRIANGLE_OFFSET_Y
import com.tonyakitori.apps.tourcompose.settings.colors.DialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultDialogBubbleColors

enum class DialogBubblePosition {
    TOP, BOTTOM
}

internal object DialogBubbleProperties {
    const val DIALOG_TAIL_OFFSET_X = 150f
    const val BUBBLE_WRAPPER_Z_INDEX = 10f

    val BUBBLE_RADIUS = 16.dp
    val TRIANGLE_OFFSET_Y = 0.5.dp
}

/**
 * Skeleton of the SpeechBubble that will be shown in the tour.
 *
 * @param modifier Modifier to be applied to the SpeechBubble.
 * @param dialogBubblePosition Position of the SpeechBubble.
 * @param dialogTailOffsetX Offset of the SpeechBubble tail.
 * @param content Content of the SpeechBubble.
 */

@Composable
fun DialogBubbleSkeleton(
    modifier: Modifier = Modifier,
    dialogBubblePosition: DialogBubblePosition = DialogBubblePosition.BOTTOM,
    dialogTailOffsetX: Float = DIALOG_TAIL_OFFSET_X,
    dialogBubbleColors: DialogBubbleColors = defaultDialogBubbleColors(),
    content: @Composable () -> Unit
) {

    val offsetX = with(LocalDensity.current) { dialogTailOffsetX.toDp() }
    val offsetY = TRIANGLE_OFFSET_Y

    Column(modifier = modifier) {

        if (dialogBubblePosition == DialogBubblePosition.BOTTOM) {
            TriangleShape(
                modifier = Modifier
                    .offset(x = offsetX, y = offsetY),
                triangleColors = dialogBubbleColors
            )
        }

        Box(
            modifier = Modifier
                .background(
                    color = dialogBubbleColors.backgroundColor,
                    shape = RoundedCornerShape(BUBBLE_RADIUS)
                )
                .fillMaxWidth()
                .zIndex(BUBBLE_WRAPPER_Z_INDEX)
        ) {
            content()
        }

        if (dialogBubblePosition == DialogBubblePosition.TOP) {
            TriangleShape(
                modifier = Modifier
                    .offset(x = offsetX, y = -offsetY),
                direction = TriangleDirection.DOWN,
                triangleColors = dialogBubbleColors
            )
        }
    }
}

@Composable
@Preview
private fun GuidedTourSkeletonTopPreview() {
    DialogBubbleSkeleton(
        modifier = Modifier.fillMaxWidth(),
        dialogBubblePosition = DialogBubblePosition.BOTTOM
    ) {
        Box(
            modifier = Modifier.padding(
                horizontal = 16.dp,
            )
        ) {
            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing")
        }
    }
}

@Composable
@Preview
private fun GuidedTourSkeletonBottomPreview() {
    DialogBubbleSkeleton(
        modifier = Modifier.fillMaxWidth(),
        dialogBubblePosition = DialogBubblePosition.TOP
    ) {
        Box(
            modifier = Modifier.padding(
                horizontal = 16.dp,
            )
        ) {
            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing")
        }
    }
}
