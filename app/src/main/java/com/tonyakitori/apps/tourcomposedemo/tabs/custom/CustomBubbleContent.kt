package com.tonyakitori.apps.tourcomposedemo.tabs.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.BubbleContentSettings

@Immutable
class CustomBubbleContent(
    override val modifier: Modifier = Modifier,
    override val title: String,
    override val description: String,
    val currentStep: Int,
    val totalSteps: Int,
    val rating: Float = 5f,
    override val onDismiss: () -> Unit,
    val onNextClick: () -> Unit,
    val onPreviousClick: () -> Unit
) : BubbleContentSettings {

    @Composable
    override fun DrawContent(modifier: Modifier) {
        CustomBubbleContentComponent(
            modifier = modifier,
            title = title,
            description = description,
            currentStep = currentStep,
            totalSteps = totalSteps,
            rating = rating,
            onDismiss = onDismiss,
            onNextClick = onNextClick,
            onPreviousClick = onPreviousClick
        )
    }
}

@Composable
private fun CustomBubbleContentComponent(
    modifier: Modifier,
    title: String,
    description: String,
    currentStep: Int,
    totalSteps: Int,
    rating: Float,
    onDismiss: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header con título y rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progreso del tour",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "$currentStep/$totalSteps",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = { currentStep.toFloat() / totalSteps.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Descripción
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                if (currentStep > 1) {
                    Button(
                        onClick = onPreviousClick,
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text("Anterior")
                    }
                }

                Button(
                    onClick = if (currentStep < totalSteps) onNextClick else onDismiss
                ) {
                    Text(if (currentStep < totalSteps) "Siguiente" else "Finalizar")
                }
            }
        }
    }
}

fun customBubbleContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    currentStep: Int,
    totalSteps: Int,
    rating: Float = 5f,
    onDismiss: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onPreviousClick: () -> Unit = {}
): BubbleContentSettings = CustomBubbleContent(
    modifier = modifier,
    title = title,
    description = description,
    currentStep = currentStep,
    totalSteps = totalSteps,
    rating = rating,
    onDismiss = onDismiss,
    onNextClick = onNextClick,
    onPreviousClick = onPreviousClick
)