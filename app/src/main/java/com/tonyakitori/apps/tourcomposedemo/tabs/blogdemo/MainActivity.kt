package com.tonyakitori.apps.tourcomposedemo.tabs.blogdemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.TourCompose
import com.tonyakitori.apps.tourcompose.controller.TourComposeController
import com.tonyakitori.apps.tourcompose.controller.TourComposeStep
import com.tonyakitori.apps.tourcompose.controller.TourComposeWrapper
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.bubbleContentBasicSettings
import java.util.UUID

@Composable
fun BlogDemoScreen() {
    MaterialTheme {
        val tourController = remember { SimpleTourController() }

        TourComposeWrapper(tourController = tourController) {

            val currentStep by tourController.currentStep.collectAsState(initial = null)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome to my awesome app!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.tourStepIndex("welcome-tour", 0)
                )

                Text(
                    text = "This app does amazing things that will change your life, cure your anxiety, and probably make you a better person.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )

                Button(
                    onClick = {
                        // This button obviously does something super important
                        println("Button clicked! 🎉")
                    },
                    modifier = Modifier.tourStepIndex("welcome-tour", 1)
                ) {
                    Text("Do Something Amazing")
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .tourStepIndex("welcome-tour", 2)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Pro Feature™",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "This card contains features so advanced that we needed a tour just to explain them.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { tourController.startTour("welcome-tour") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("🎯 Start the Tour")
                }
            }

            // Here's where the magic happens ✨
            TourCompose(
                componentRectArea = currentStep?.componentRect,
                bubbleContentSettings = currentStep?.bubbleContentSettings
            )
        }
    }
}

// This controller is so simple, it's almost embarrassing how easy this is
class SimpleTourController : TourComposeController() {
    init {
        addTour(
            flowId = "welcome-tour",
            steps = listOf(
                TourComposeStep(
                    id = UUID.randomUUID().toString(),
                    bubbleContentSettings = bubbleContentBasicSettings(
                        title = "👋 Hey There!",
                        description = "This is your app's main title. It's probably the first thing users see, so we made it big and friendly. Good choice!",
                        primaryButtonText = "Makes Sense",
                        onPrimaryClick = { nextStep() },
                        onDismiss = { stopTour() }
                    )
                ),
                TourComposeStep(
                    id = UUID.randomUUID().toString(),
                    bubbleContentSettings = bubbleContentBasicSettings(
                        title = "🚀 The Action Button",
                        description = "This button is where the magic happens. Click it to do something amazing (results may vary, but we're optimistic).",
                        primaryButtonText = "Got It!",
                        secondaryButtonText = "Wait, Go Back",
                        onPrimaryClick = { nextStep() },
                        onSecondaryClick = { previousStep() },
                        onDismiss = { stopTour() }
                    )
                ),
                TourComposeStep(
                    id = UUID.randomUUID().toString(),
                    bubbleContentSettings = bubbleContentBasicSettings(
                        title = "💎 Premium Content",
                        description = "This card showcases your premium features. It's fancy, it's important, and now your users know exactly where to find it!",
                        primaryButtonText = "Awesome, I'm Done!",
                        secondaryButtonText = "Show Me Again",
                        onPrimaryClick = { stopTour() },
                        onSecondaryClick = { previousStep() },
                        onDismiss = { stopTour() }
                    )
                )
            )
        )
    }
}