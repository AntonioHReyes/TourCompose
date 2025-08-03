package com.tonyakitori.apps.tourcomposedemo.tabs.basicdemo

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
import com.tonyakitori.apps.tourcompose.TourCompose
import com.tonyakitori.apps.tourcompose.controller.LocalTourController
import com.tonyakitori.apps.tourcompose.controller.TourComposeScope
import com.tonyakitori.apps.tourcompose.settings.TourComposeProperties
import com.tonyakitori.apps.tourcomposedemo.R

/**
 * Shows basic TourCompose - design system agnostic
 * Uses simple colors and basic Compose components
 *
 * Note: UI texts are kept in Spanish as a tribute to the native language of the developer.
 */
@Composable
fun TourComposeScope.BasicDemoContent(
    modifier: Modifier = Modifier
) {
    val tourController = LocalTourController.current
    val step by tourController.currentStep.collectAsState(initial = null)
    var textValue by remember { mutableStateOf("Sample Text") }
    var switchEnabled by remember { mutableStateOf(false) }

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
                    text = "TourCompose Básico",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Agnóstico al sistema de diseño - Colores básicos predefinidos",
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
                            BasicDemoController.BASIC_DEMO_FLOW,
                            BasicDemoSteps.IMAGE.stepIndex
                        ),
                    painter = painterResource(id = R.drawable.app_tour),
                    contentDescription = "Imagen de ejemplo"
                )

                Button(
                    modifier = Modifier
                        .tourStepIndex(
                            BasicDemoController.BASIC_DEMO_FLOW,
                            BasicDemoSteps.BUTTON.stepIndex
                        ),
                    onClick = {
                        tourController.startTour(BasicDemoController.BASIC_DEMO_FLOW)
                    }
                ) {
                    Text("Iniciar Tour")
                }
            }

            TextField(
                value = textValue,
                onValueChange = { textValue = it },
                label = { Text("Campo de texto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .tourStepIndex(
                        BasicDemoController.BASIC_DEMO_FLOW,
                        BasicDemoSteps.TEXT_FIELD.stepIndex
                    )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Switch de ejemplo")
                Switch(
                    checked = switchEnabled,
                    onCheckedChange = { switchEnabled = it },
                    modifier = Modifier.tourStepIndex(
                        BasicDemoController.BASIC_DEMO_FLOW,
                        BasicDemoSteps.SWITCH.stepIndex
                    )
                )
            }

            // Tour overlay implementation
            step?.let {
                TourCompose(
                    tourComposeProperties = TourComposeProperties.getDefaultInstance(),
                    componentRectArea = it.componentRect,
                    bubbleContentSettings = it.bubbleContentSettings
                )
            }
        }
    }
}