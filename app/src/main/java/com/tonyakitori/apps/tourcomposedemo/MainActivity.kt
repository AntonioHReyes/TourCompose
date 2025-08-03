package com.tonyakitori.apps.tourcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tonyakitori.apps.tourcompose.controller.TourComposeWrapper
import com.tonyakitori.apps.tourcompose.settings.colors.defaultBubbleContentColors
import com.tonyakitori.apps.tourcomposedemo.tabs.basicdemo.BasicDemoContent
import com.tonyakitori.apps.tourcomposedemo.tabs.basicdemo.BasicDemoController
import com.tonyakitori.apps.tourcomposedemo.tabs.blogdemo.BlogDemoScreen
import com.tonyakitori.apps.tourcomposedemo.tabs.custom.CustomDemoContent
import com.tonyakitori.apps.tourcomposedemo.tabs.custom.CustomDemoController
import com.tonyakitori.apps.tourcomposedemo.tabs.material3.Material3DemoContent
import com.tonyakitori.apps.tourcomposedemo.tabs.material3.Material3DemoController
import com.tonyakitori.apps.tourcomposedemo.ui.theme.TourComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var enableDarkTheme by remember { mutableStateOf(false) }
            val customColors = defaultBubbleContentColors().copy(
                titleTextColor = androidx.compose.ui.graphics.Color(0xFFE91E63)
            )

            TourComposeTheme(darkTheme = enableDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("TourCompose Demo") },
                                actions = {
                                    IconButton(
                                        onClick = { enableDarkTheme = !enableDarkTheme }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_bg_dark_mode),
                                            contentDescription = "Toggle Dark Mode"
                                        )
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        DemoTabContainer(
                            modifier = Modifier.padding(paddingValues),
                            basicContent = {
                                TourComposeWrapper(
                                    tourController = remember {
                                        BasicDemoController(customColors)
                                    }
                                ) {
                                    BasicDemoContent()
                                }
                            },
                            material3Content = {
                                TourComposeWrapper(
                                    tourController = remember {
                                        Material3DemoController()
                                    }
                                ) {
                                    Material3DemoContent()
                                }
                            },
                            customContent = {
                                TourComposeWrapper(
                                    tourController = remember {
                                        CustomDemoController()
                                    }
                                ) {
                                    CustomDemoContent()
                                }
                            },
                            blogDemoContent = {
                                BlogDemoScreen()
                            }
                        )
                    }
                }
            }
        }
    }
}