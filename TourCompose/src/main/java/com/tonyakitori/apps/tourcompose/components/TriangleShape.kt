package com.tonyakitori.apps.tourcompose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.components.TriangleShapeProperties.TRIANGLE_ARC_POINTER_Y_PADDING
import com.tonyakitori.apps.tourcompose.components.TriangleShapeProperties.TRIANGLE_ARC_RADIUS
import com.tonyakitori.apps.tourcompose.components.TriangleShapeProperties.TRIANGLE_CENTER_AXIS_BASE_WIDTH
import com.tonyakitori.apps.tourcompose.components.TriangleShapeProperties.TRIANGLE_HEIGHT
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultDialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.DialogBubbleColors

enum class TriangleDirection {
    UP, DOWN
}

internal object TriangleShapeProperties {
    const val TRIANGLE_HEIGHT = 12f
    const val TRIANGLE_CENTER_AXIS_BASE_WIDTH = 8f
    const val TRIANGLE_ARC_RADIUS = 4f
    const val TRIANGLE_ARC_POINTER_Y_PADDING = 5f
}

/**
 * Draws a triangle shape with a pointer in the middle.
 *
 * @param modifier Modifier to be applied to the triangle.
 * @param direction Direction of the triangle pointer.
 * @param color Color of the triangle.
 */
@Composable
fun TriangleShape(
    modifier: Modifier = Modifier,
    direction: TriangleDirection = TriangleDirection.UP,
    triangleColors: DialogBubbleColors = DefaultDialogBubbleColors()
) {
    Canvas(
        modifier = modifier
            .height(TRIANGLE_HEIGHT.dp)
    ) {
        val centerAxis = size.width / 2
        val height = size.height

        val leftBase = Offset(
            x = centerAxis - TRIANGLE_CENTER_AXIS_BASE_WIDTH.dp.toPx(),
            y = if (direction == TriangleDirection.UP) height else 0f
        )
        val rightBase = Offset(
            x = centerAxis + TRIANGLE_CENTER_AXIS_BASE_WIDTH.dp.toPx(),
            y = if (direction == TriangleDirection.UP) height else 0f
        )

        val arcStart = Offset(
            x = centerAxis - TRIANGLE_ARC_RADIUS,
            y = if (direction == TriangleDirection.UP) {
                TRIANGLE_ARC_POINTER_Y_PADDING
            } else {
                height - TRIANGLE_ARC_POINTER_Y_PADDING
            }
        )
        val arcEnd = Offset(
            x = centerAxis + TRIANGLE_ARC_RADIUS,
            y = if (direction == TriangleDirection.UP) {
                TRIANGLE_ARC_POINTER_Y_PADDING
            } else {
                height - TRIANGLE_ARC_POINTER_Y_PADDING
            }
        )

        val path = Path().apply {
            moveTo(leftBase.x, leftBase.y)
            lineTo(arcStart.x, arcStart.y) // Línea hasta el comienzo del arco

            quadraticBezierTo(
                x1 = centerAxis,
                y1 = if (direction == TriangleDirection.UP) 0f else height,
                x2 = arcEnd.x,
                y2 = arcEnd.y
            )

            lineTo(rightBase.x, rightBase.y)

            close()
        }

        drawPath(
            path = path,
            color = triangleColors.backgroundColor,
        )
    }
}

@Composable
@Preview
internal fun TriangleShapeUpPreview() {
    TriangleShape(
        modifier = Modifier
            .width(30.dp)
    )
}

@Composable
@Preview
internal fun TriangleShapeDownPreview() {
    TriangleShape(
        modifier = Modifier
            .width(30.dp),
        direction = TriangleDirection.DOWN
    )
}
