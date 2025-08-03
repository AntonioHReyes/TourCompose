package com.tonyakitori.apps.tourcompose.material3.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.R
import com.tonyakitori.apps.tourcompose.components.DialogBubbleSkeleton
import com.tonyakitori.apps.tourcompose.material3.colors.material3BubbleContentColors
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.BubbleContentSettings
import com.tonyakitori.apps.tourcompose.settings.colors.BubbleContentColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultDialogBubbleColors

/**
 * Material3-specific interface for bubble content settings that automatically
 * integrates with Material3 theme and components.
 */
@Stable
interface BubbleContentMaterial3Interface : BubbleContentSettings {
    val primaryButtonText: String?
    val secondaryButtonText: String?

    //actions
    val onPrimaryClick: () -> Unit
    val onSecondaryClick: () -> Unit
    val colors: BubbleContentColors?
}

/**
 * Material3 implementation of BubbleContentSettings that uses Material3 components
 * and automatically applies Material3 theming.
 */
@Immutable
class BubbleContentMaterial3(
    override val modifier: Modifier = Modifier,
    override val title: String,
    override val description: String,
    override val primaryButtonText: String?,
    override val secondaryButtonText: String?,
    override val onDismiss: () -> Unit,
    override val onPrimaryClick: () -> Unit,
    override val onSecondaryClick: () -> Unit,
    override val colors: BubbleContentColors? = null
) : BubbleContentMaterial3Interface {

    @Composable
    override fun DrawContent(
        modifier: Modifier
    ) {
        BubbleContentMaterial3Component(
            modifier = modifier,
            title = title,
            description = description,
            primaryButtonText = primaryButtonText,
            secondaryButtonText = secondaryButtonText,
            onDismiss = onDismiss,
            onPrimaryClick = onPrimaryClick,
            onSecondaryClick = onSecondaryClick,
            colors = colors ?: material3BubbleContentColors()
        )
    }
}

/**
 * Creates a Material3 BubbleContentSettings with automatic Material3 theming.
 *
 * This function provides a convenient way to create bubble content that automatically
 * uses Material3 colors, typography, and components.
 *
 * @param modifier Modifier to be applied to the content
 * @param title The title text to display
 * @param description The description text to display
 * @param primaryButtonText Text for the primary action button (optional)
 * @param secondaryButtonText Text for the secondary action button (optional)
 * @param onDismiss Callback when the close button is clicked
 * @param onPrimaryClick Callback when the primary button is clicked
 * @param onSecondaryClick Callback when the secondary button is clicked
 * @param colors Custom colors, if not provided uses Material3 theme colors
 */
fun bubbleContentMaterial3(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    primaryButtonText: String? = null,
    secondaryButtonText: String? = null,
    onDismiss: () -> Unit = {},
    onPrimaryClick: () -> Unit = {},
    onSecondaryClick: () -> Unit = {},
    colors: BubbleContentColors? = null
): BubbleContentSettings = BubbleContentMaterial3(
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
private fun BubbleContentMaterial3Component(
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
            .padding(
                bottom = 6.dp
            )
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 12.dp)
                    .semantics(mergeDescendants = true) {}
            ) {
                Text(
                    text = title,
                    color = colors.titleTextColor,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    text = description,
                    color = colors.descriptionTextColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = onDismiss) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_tour_compose_close),
                    contentDescription = null,
                    tint = colors.iconTintColor,
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = colors.dividerColor
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            secondaryButtonText?.let {
                Button(
                    onClick = onSecondaryClick,
                ) {
                    Text(text = secondaryButtonText)
                }
            }

            primaryButtonText?.let {
                Spacer(modifier = Modifier.size(16.dp))

                Button(onClick = onPrimaryClick) {
                    Text(text = primaryButtonText)
                }
            }
        }
    }
}

@Composable
@Preview
private fun BubbleContentMaterial3Preview() {
    DialogBubbleSkeleton(
        dialogBubbleColors = defaultDialogBubbleColors()
    ) {
        BubbleContentMaterial3Component(
            modifier = Modifier,
            title = "Material3 Title",
            description = "This is a Material3 styled bubble content with automatic theming",
            onDismiss = {},
            primaryButtonText = "Got it",
            secondaryButtonText = "Skip",
            onPrimaryClick = {},
            onSecondaryClick = {},
            colors = material3BubbleContentColors()
        )
    }
}