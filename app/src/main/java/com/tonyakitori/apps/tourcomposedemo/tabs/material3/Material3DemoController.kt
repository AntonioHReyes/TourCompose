package com.tonyakitori.apps.tourcomposedemo.tabs.material3

import com.tonyakitori.apps.tourcompose.controller.TourComposeController
import com.tonyakitori.apps.tourcompose.controller.TourComposeStep
import com.tonyakitori.apps.tourcompose.material3.content.bubbleContentMaterial3
import java.util.UUID

/**
 * Specific controller for TourComposeMaterial3 demo.
 * Handles tours that automatically integrate with Material3 theme.
 *
 * Note: Tour step texts are kept in Spanish as a tribute to the native language of the developer.
 */
class Material3DemoController : TourComposeController() {

    init {
        addTour(
            flowId = MATERIAL3_DEMO_FLOW,
            steps = createMaterial3DemoSteps()
        )
    }

    private fun createMaterial3DemoSteps(): List<TourComposeStep> = listOf(
        TourComposeStep(
            id = Material3DemoSteps.IMAGE.id.toString(),
            bubbleContentSettings = bubbleContentMaterial3(
                title = "🎨 Imagen Material3",
                description = "Esta demostración usa TourComposeMaterial3 que se integra automáticamente con tu tema Material3. Los colores se adaptan automáticamente al tema claro/oscuro.",
                primaryButtonText = "Siguiente",
                secondaryButtonText = null,
                onPrimaryClick = { nextStep() },
                onDismiss = { stopTour() }
            )
        ),
        TourComposeStep(
            id = Material3DemoSteps.BUTTON.id.toString(),
            bubbleContentSettings = bubbleContentMaterial3(
                title = "▶️ Botón Material3",
                description = "Este botón inicia el tour Material3. Observa cómo los colores del bubble se adaptan automáticamente al esquema de colores de Material3.",
                primaryButtonText = "Siguiente",
                secondaryButtonText = "Anterior",
                onPrimaryClick = { nextStep() },
                onSecondaryClick = { previousStep() },
                onDismiss = { stopTour() }
            )
        ),
        TourComposeStep(
            id = Material3DemoSteps.TEXT_FIELD.id.toString(),
            bubbleContentSettings = bubbleContentMaterial3(
                title = "📝 Campo Material3",
                description = "Los TextField de Material3 se integran perfectamente. El bubble usa automáticamente surface, onSurface, primary y otros colores del tema actual.",
                primaryButtonText = "Siguiente",
                secondaryButtonText = "Anterior",
                onPrimaryClick = { nextStep() },
                onSecondaryClick = { previousStep() },
                onDismiss = { stopTour() }
            )
        ),
        TourComposeStep(
            id = Material3DemoSteps.SWITCH.id.toString(),
            bubbleContentSettings = bubbleContentMaterial3(
                title = "🎛️ Switch Material3",
                description = "¡Perfecto! Has completado el tour Material3. La integración automática hace que no tengas que preocuparte por los colores - siempre se ven bien con tu tema.",
                primaryButtonText = "¡Excelente!",
                secondaryButtonText = "Anterior",
                onPrimaryClick = { stopTour() },
                onSecondaryClick = { previousStep() },
                onDismiss = { stopTour() }
            )
        )
    )

    companion object {
        const val MATERIAL3_DEMO_FLOW = "material3_demo_flow"
    }
}

enum class Material3DemoSteps(val id: UUID, val stepIndex: Int) {
    IMAGE(UUID.randomUUID(), 0),
    BUTTON(UUID.randomUUID(), 1),
    TEXT_FIELD(UUID.randomUUID(), 2),
    SWITCH(UUID.randomUUID(), 3)
}