package com.tonyakitori.apps.tourcomposedemo.tabs.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.TourCompose
import com.tonyakitori.apps.tourcompose.controller.LocalTourController
import com.tonyakitori.apps.tourcompose.controller.TourComposeScope
import com.tonyakitori.apps.tourcompose.settings.TourComposeProperties
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultDialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultSpotlightColors
import com.tonyakitori.apps.tourcomposedemo.R

/**
 * Shows advanced TourCompose customization
 * Custom colors, personalized components, etc.
 *
 * Note: UI texts are kept in Spanish as a tribute to the native language of the developer.
 */
@Composable
fun TourComposeScope.CustomDemoContent(
    modifier: Modifier = Modifier
) {
    val tourController = LocalTourController.current
    val step by tourController.currentStep.collectAsState(initial = null)
    var textValue by remember { mutableStateOf("Custom Text") }
    var switchEnabled by remember { mutableStateOf(false) }

    val customTourProperties = TourComposeProperties.getDefaultInstance().copy(
        spotlightColors = DefaultSpotlightColors(
            overlayBackgroundColor = Color(0xFF1A237E).copy(alpha = 0.9f), // Dark blue
            overlayBorderColor = Color(0xFFFFEB3B) // Yellow
        ),
        dialogBubbleColors = DefaultDialogBubbleColors(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        )
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header section
            Column {
                Text(
                    text = "Personalización Avanzada",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFE91E63)
                )
                Text(
                    text = "Colores custom, componentes personalizados y máxima flexibilidad",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Demo UI components
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(70.dp)
                        .tourStepIndex(
                            CustomDemoController.CUSTOM_DEMO_FLOW,
                            CustomDemoSteps.IMAGE.stepIndex
                        ),
                    painter = painterResource(id = R.drawable.app_tour),
                    contentDescription = "Imagen de ejemplo"
                )

                Button(
                    modifier = Modifier
                        .tourStepIndex(
                            CustomDemoController.CUSTOM_DEMO_FLOW,
                            CustomDemoSteps.BUTTON.stepIndex
                        ),
                    onClick = {
                        tourController.startTour(CustomDemoController.CUSTOM_DEMO_FLOW)
                    }
                ) {
                    Text("Tour Custom")
                }
            }

            TextField(
                value = textValue,
                onValueChange = { textValue = it },
                label = { Text("Campo Personalizado") },
                modifier = Modifier
                    .fillMaxWidth()
                    .tourStepIndex(
                        CustomDemoController.CUSTOM_DEMO_FLOW,
                        CustomDemoSteps.TEXT_FIELD.stepIndex
                    )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Switch Personalizado")
                Switch(
                    checked = switchEnabled,
                    onCheckedChange = { switchEnabled = it },
                    modifier = Modifier.tourStepIndex(
                        CustomDemoController.CUSTOM_DEMO_FLOW,
                        CustomDemoSteps.SWITCH.stepIndex
                    )
                )
            }

            // Tour overlay with custom properties
            step?.let {
                TourCompose(
                    tourComposeProperties = customTourProperties,
                    componentRectArea = it.componentRect,
                    bubbleContentSettings = it.bubbleContentSettings,
                )
            }
        }
    }
}