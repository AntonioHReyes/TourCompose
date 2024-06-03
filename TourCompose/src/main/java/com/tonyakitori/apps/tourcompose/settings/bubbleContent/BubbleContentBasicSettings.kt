package com.tonyakitori.apps.tourcompose.settings.bubbleContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.tonyakitori.apps.tourcompose.components.DialogBubblePosition
import com.tonyakitori.apps.tourcompose.components.DialogBubbleSkeleton
import com.tonyakitori.apps.tourcompose.settings.colors.DialogBubbleColors

@Stable
interface BubbleContentBasicSettingsInterface : BubbleContentSettings {
    val primaryButtonText: String?
    val secondaryButtonText: String?

    //actions
    val onPrimaryClick: () -> Unit
    val onSecondaryClick: () -> Unit
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
    override val onSecondaryClick: () -> Unit
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
            onSecondaryClick = onSecondaryClick
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
    onSecondaryClick: () -> Unit = {}
): BubbleContentSettings = BubbleContentBasicSettings(
    modifier = modifier,
    title = title,
    description = description,
    onDismiss = onDismiss,
    primaryButtonText = primaryButtonText,
    secondaryButtonText = secondaryButtonText,
    onPrimaryClick = onPrimaryClick,
    onSecondaryClick = onSecondaryClick
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
    onSecondaryClick: () -> Unit
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
                    .padding(top = 12.dp)
                    .semantics(mergeDescendants = true) {}
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    text = description,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = onDismiss) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_tour_compose_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
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
private fun BubbleContentBasicSettingsPreview() {
    DialogBubbleSkeleton {
        BubbleContentBasicSettingsComponent(
            modifier = Modifier,
            title = "Title",
            description = "Description",
            onDismiss = {},
            primaryButtonText = "Cancelar",
            secondaryButtonText = null,
            onPrimaryClick = {},
            onSecondaryClick = {}
        )
    }
}