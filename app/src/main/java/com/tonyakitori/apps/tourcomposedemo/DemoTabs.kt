package com.tonyakitori.apps.tourcomposedemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class DemoTab(val title: String, val description: String) {
    BASIC("Básico", "TourCompose agnóstico - sin sistema de diseño específico"),
    MATERIAL3("Material3", "TourComposeMaterial3 - integración automática con Material3"),
    CUSTOM("Personalizado", "Personalización avanzada - colores y estilos custom"),
    BLOG_DEMO("Blog Demo", "The example used in the blog post")
}

@Composable
fun DemoTabContainer(
    modifier: Modifier = Modifier,
    basicContent: @Composable () -> Unit,
    material3Content: @Composable () -> Unit,
    customContent: @Composable () -> Unit,
    blogDemoContent: @Composable () -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = DemoTab.entries

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Tab Row
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 16.dp
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        )
                        Text(
                            text = tab.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )
                    }
                }
            }
        }

        // Tab Content
        when (selectedTabIndex) {
            0 -> basicContent()
            1 -> material3Content()
            2 -> customContent()
            3 -> blogDemoContent()
        }
    }
}