package com.tonyakitori.apps.tourcomposedemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tonyakitori.apps.tourcompose.TourCompose
import com.tonyakitori.apps.tourcompose.controller.LocalTourController
import com.tonyakitori.apps.tourcompose.controller.TourComposeScope
import com.tonyakitori.apps.tourcompose.controller.TourComposeWrapper
import com.tonyakitori.apps.tourcompose.settings.TourComposeProperties
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultDialogBubbleColors
import com.tonyakitori.apps.tourcompose.settings.colors.DefaultSpotlightColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultBubbleContentColors
import com.tonyakitori.apps.tourcompose.settings.colors.defaultDialogBubbleColors
import com.tonyakitori.apps.tourcomposedemo.TourComposeAppController.Companion.COMPLETE_TOUR_COMPOSE_FLOW
import com.tonyakitori.apps.tourcomposedemo.TourComposeAppController.Companion.TITLE_AND_IMAGE_FLOW
import com.tonyakitori.apps.tourcomposedemo.ui.theme.TourComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            var enableDarkTheme by remember { mutableStateOf(false) }
            var enableCustomColors by remember { mutableStateOf(false) }
            var useCustomBubbleContent by remember { mutableStateOf(false) }

            val contentBubbleColors = defaultBubbleContentColors().copy(
                titleTextColor = MaterialTheme.colorScheme.error
            )

            TourComposeTheme(
                darkTheme = enableDarkTheme
            ) {
                TourComposeWrapper(
                    tourController = remember(useCustomBubbleContent, enableCustomColors) {
                        TourComposeAppController(
                            useCustomBubbleContent,
                            if (enableCustomColors) contentBubbleColors else null
                        )
                    },
                ) {

                    val tourController = LocalTourController.current
                    val step by tourController.currentStep.collectAsState(initial = null)

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Tour Compose")
                                },
                                actions = {
                                    IconButton(
                                        modifier = Modifier
                                            .tourStepIndex(
                                                COMPLETE_TOUR_COMPOSE_FLOW,
                                                CompleteTourComposeSteps.CHANGE_THEME.stepIndex
                                            ),
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_bg_dark_mode),
                                                contentDescription = "Enable Dark Mode",
                                            )
                                        },
                                        onClick = { enableDarkTheme = !enableDarkTheme }
                                    )
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            MainScreen(
                                modifier = Modifier,
                                enableCustomColors = enableCustomColors,
                                onCustomColorsChange = { enableCustomColors = it },
                                useCustomBubbleContent = useCustomBubbleContent,
                                onCustomBubbleContentChange = { useCustomBubbleContent = it },
                                onStartTour = {
                                    tourController.startTour(TITLE_AND_IMAGE_FLOW)
                                },
                                onStartCompleteTour = {
                                    tourController.startTour(COMPLETE_TOUR_COMPOSE_FLOW)
                                }
                            )
                        }
                    }

                    TourCompose(
                        componentRectArea = step?.componentRect,
                        bubbleContentSettings = step?.bubbleContentSettings,
                        tourComposeProperties = if (enableCustomColors) {
                            TourComposeProperties.getDefaultInstance().copy(
                                spotlightColors = DefaultSpotlightColors(
                                    overlayBackgroundColor = MaterialTheme.colorScheme.inversePrimary.copy(
                                        alpha = 0.7f
                                    ),
                                    overlayBorderColor = MaterialTheme.colorScheme.error
                                ),
                                dialogBubbleColors = DefaultDialogBubbleColors(
                                    backgroundColor = MaterialTheme.colorScheme.errorContainer
                                )
                            )
                        } else {
                            TourComposeProperties.getDefaultInstance().copy(
                                dialogBubbleColors = if (useCustomBubbleContent) {
                                    DefaultDialogBubbleColors(
                                        backgroundColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                } else defaultDialogBubbleColors()
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TourComposeScope.MainScreen(
    modifier: Modifier = Modifier,
    enableCustomColors: Boolean = false,
    onCustomColorsChange: (Boolean) -> Unit = {},
    useCustomBubbleContent: Boolean = false,
    onCustomBubbleContentChange: (Boolean) -> Unit = {},
    onStartTour: () -> Unit = {},
    onStartCompleteTour: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            modifier = Modifier
                .size(150.dp)
                .tourStepIndex(TITLE_AND_IMAGE_FLOW, TitleAndImageSteps.IMAGE.stepIndex)
                .tourStepIndex(
                    COMPLETE_TOUR_COMPOSE_FLOW,
                    CompleteTourComposeSteps.SIMPLE_IMAGE.stepIndex
                ),
            painter = painterResource(id = R.drawable.app_tour),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .padding(16.dp)
                .tourStepIndex(TITLE_AND_IMAGE_FLOW, TitleAndImageSteps.TITLE.stepIndex),
            text = "TourCompose preview!",
            style = MaterialTheme.typography.headlineSmall,
        )

        Column {
            //Form components
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Name") },
                placeholder = { Text("Enter your name") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .tourStepIndex(
                        COMPLETE_TOUR_COMPOSE_FLOW,
                        CompleteTourComposeSteps.AN_INPUT.stepIndex
                    )
            )

            // Demo controls
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Demo Controls",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Custom Colors")
                    Switch(
                        checked = enableCustomColors,
                        onCheckedChange = onCustomColorsChange
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Custom Bubble Content")
                    Switch(
                        checked = useCustomBubbleContent,
                        onCheckedChange = onCustomBubbleContentChange
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Demo Switch")
                    Switch(
                        modifier = Modifier
                            .tourStepIndex(
                                COMPLETE_TOUR_COMPOSE_FLOW,
                                CompleteTourComposeSteps.A_SWITCH.stepIndex
                            ),
                        checked = true,
                        onCheckedChange = {}
                    )
                }
            }

        }

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp,
                        horizontal = 32.dp
                    ),
                onClick = onStartTour
            ) {
                Text(text = "Start Tour")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                onClick = onStartCompleteTour
            ) {
                Text(text = "Start Complete Tour")
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun MainScreenPreview() {
    TourComposeTheme {
        Surface {
            TourComposeWrapper(
                tourController = TourComposeAppController(),
            ) {
                MainScreen()
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun MainScreenDarkPreview() {
    TourComposeTheme {
        Surface {
            TourComposeWrapper(
                tourController = TourComposeAppController(),
            ) {
                MainScreen()
            }
        }
    }
}