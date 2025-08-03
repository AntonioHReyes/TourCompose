package com.tonyakitori.apps.tourcompose.settings.bubbleContent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tonyakitori.apps.tourcompose.components.DialogBubbleSkeleton
import com.tonyakitori.apps.tourcompose.settings.colors.BubbleContentColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultBubbleContentColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultDialogBubbleColors

@Stable
interface BubbleContentBasicSettingsInterface : BubbleContentSettings {
    val primaryButtonText: String?
    val secondaryButtonText: String?

    //actions
    val onPrimaryClick: () -> Unit
    val onSecondaryClick: () -> Unit
    val colors: BubbleContentColors?
}

@Immutable
class BubbleContentBasicSettings(
    override val modifier: Modifier = Modifier,
    override val title: String,
    override val description: String,
    override val primaryButtonText: String?,
    override val secondaryButtonText: String?,
    override val onDismiss: () -> Unit,
    override val onPrimaryClick: () -> Unit,
    override val onSecondaryClick: () -> Unit,
    override val colors: BubbleContentColors? = null
) : BubbleContentBasicSettingsInterface {

    @Composable
    override fun DrawContent(
        modifier: Modifier
    ) {
        BubbleContentBasicSettingsComponent(
            modifier = modifier,
            title = title,
            description = description,
            primaryButtonText = primaryButtonText,
            secondaryButtonText = secondaryButtonText,
            onDismiss = onDismiss,
            onPrimaryClick = onPrimaryClick,
            onSecondaryClick = onSecondaryClick,
            colors = colors ?: defaultBubbleContentColors()
        )
    }


}

fun bubbleContentBasicSettings(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    primaryButtonText: String? = null,
    secondaryButtonText: String? = null,
    onDismiss: () -> Unit = {},
    onPrimaryClick: () -> Unit = {},
    onSecondaryClick: () -> Unit = {},
    colors: BubbleContentColors? = null
): BubbleContentSettings = BubbleContentBasicSettings(
    modifier = modifier,
    title = title,
    description = description,
    onDismiss = onDismiss,
    primaryButtonText = primaryButtonText,
    secondaryButtonText = secondaryButtonText,
    onPrimaryClick = onPrimaryClick,
    onSecondaryClick = onSecondaryClick,
    colors = colors
)

@Composable
private fun BubbleContentBasicSettingsComponent(
    modifier: Modifier,
    title: String,
    description: String,
    primaryButtonText: String? = null,
    secondaryButtonText: String? = null,
    onDismiss: () -> Unit,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    colors: BubbleContentColors
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 12.dp,
            )
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .semantics(mergeDescendants = true) {},
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BasicText(
                    text = title,
                    style = TextStyle(
                        color = colors.titleTextColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                BasicText(
                    text = description,
                    style = TextStyle(
                        color = colors.descriptionTextColor,
                        fontSize = 14.sp
                    )
                )
            }

            // Simple close button using Box
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onDismiss() }
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = "✕",
                    style = TextStyle(
                        color = colors.iconTintColor,
                        fontSize = 16.sp
                    )
                )
            }
        }

        // Simple divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(colors.dividerColor)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.End
        ){
            secondaryButtonText?.let {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .clickable { onSecondaryClick() }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = secondaryButtonText,
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            primaryButtonText?.let {
                Spacer(modifier = Modifier.size(16.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Blue)
                        .clickable { onPrimaryClick() }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = primaryButtonText,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun BubbleContentBasicSettingsPreview() {
    DialogBubbleSkeleton(
        dialogBubbleColors = defaultDialogBubbleColors()
    ) {
        BubbleContentBasicSettingsComponent(
            modifier = Modifier,
            title = "Title",
            description = "Description",
            onDismiss = {},
            primaryButtonText = "Cancelar",
            secondaryButtonText = null,
            onPrimaryClick = {},
            onSecondaryClick = {},
            colors = defaultBubbleContentColors()
        )
    }
}