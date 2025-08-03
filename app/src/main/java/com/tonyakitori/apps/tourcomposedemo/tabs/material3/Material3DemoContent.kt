package com.tonyakitori.apps.tourcomposedemo.tabs.material3

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.controller.LocalTourController
import com.tonyakitori.apps.tourcompose.controller.TourComposeScope
import com.tonyakitori.apps.tourcompose.material3.TourComposeMaterial3
import com.tonyakitori.apps.tourcomposedemo.R

/**
 * Shows TourComposeMaterial3 - automatic Material3 integration
 * Colors automatically adapt to the current Material3 theme
 *
 * Note: UI texts are kept in Spanish as a tribute to the native language of the developer.
 */
@Composable
fun TourComposeScope.Material3DemoContent(
    modifier: Modifier = Modifier
) {
    val tourController = LocalTourController.current
    val step by tourController.currentStep.collectAsState(initial = null)
    var textValue by remember { mutableStateOf("Material3 Text") }
    var switchEnabled by remember { mutableStateOf(true) }

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
                    text = "TourCompose Material3",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Integración automática con Material3 - Los colores se adaptan al tema",
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
                            Material3DemoController.MATERIAL3_DEMO_FLOW,
                            Material3DemoSteps.IMAGE.stepIndex
                        ),
                    painter = painterResource(id = R.drawable.app_tour),
                    contentDescription = "Imagen de ejemplo"
                )

                Button(
                    modifier = Modifier
                        .tourStepIndex(
                            Material3DemoController.MATERIAL3_DEMO_FLOW,
                            Material3DemoSteps.BUTTON.stepIndex
                        ),
                    onClick = {
                        tourController.startTour(Material3DemoController.MATERIAL3_DEMO_FLOW)
                    }
                ) {
                    Text("Tour Material3")
                }
            }

            TextField(
                value = textValue,
                onValueChange = { textValue = it },
                label = { Text("Campo Material3") },
                modifier = Modifier
                    .fillMaxWidth()
                    .tourStepIndex(
                        Material3DemoController.MATERIAL3_DEMO_FLOW,
                        Material3DemoSteps.TEXT_FIELD.stepIndex
                    )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Switch Material3")
                Switch(
                    checked = switchEnabled,
                    onCheckedChange = { switchEnabled = it },
                    modifier = Modifier.tourStepIndex(
                        Material3DemoController.MATERIAL3_DEMO_FLOW,
                        Material3DemoSteps.SWITCH.stepIndex
                    )
                )
            }

            // Tour overlay with automatic Material3 integration
            step?.let {
                TourComposeMaterial3(
                    componentRectArea = it.componentRect,
                    bubbleContentSettings = it.bubbleContentSettings,
                )
            }
        }
    }
}