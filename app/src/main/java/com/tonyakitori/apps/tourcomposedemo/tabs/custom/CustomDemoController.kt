package com.tonyakitori.apps.tourcomposedemo.tabs.custom

import com.tonyakitori.apps.tourcompose.controller.TourComposeController
import com.tonyakitori.apps.tourcompose.controller.TourComposeStep
import java.util.UUID

/**
 * Specific controller for advanced customization demo.
 * Handles tours with fully customized components and custom colors.
 *
 * Note: Tour step texts are kept in Spanish as a tribute to the native language of the developer.
 */
class CustomDemoController() : TourComposeController() {

    init {
        addTour(
            flowId = CUSTOM_DEMO_FLOW,
            steps = createCustomDemoSteps()
        )
    }

    private fun createCustomDemoSteps(): List<TourComposeStep> = listOf(
        TourComposeStep(
            id = CustomDemoSteps.IMAGE.id.toString(),
            bubbleContentSettings = CustomBubbleContent(
                title = "🎨 Imagen Personalizada",
                description = "Esta demostración muestra la flexibilidad total de TourCompose. Componentes completamente personalizados con progreso visual y ratings.",
                currentStep = 1,
                totalSteps = 4,
                rating = 4.8f,
                onDismiss = { stopTour() },
                onNextClick = { nextStep() },
                onPreviousClick = { },
            )
        ),
        TourComposeStep(
            id = CustomDemoSteps.BUTTON.id.toString(),
            bubbleContentSettings = CustomBubbleContent(
                title = "🚀 Botón Avanzado",
                description = "Este botón forma parte de una experiencia completamente personalizada. Observa el progreso visual y la barra de rating únicos.",
                currentStep = 2,
                totalSteps = 4,
                rating = 4.9f,
                onDismiss = { stopTour() },
                onNextClick = { nextStep() },
                onPreviousClick = { previousStep() }
            )
        ),
        TourComposeStep(
            id = CustomDemoSteps.TEXT_FIELD.id.toString(),
            bubbleContentSettings = CustomBubbleContent(
                title = "✨ Campo Único",
                description = "Los componentes personalizados te permiten crear experiencias únicas que se adaptan perfectamente a tu marca y diseño específico.",
                currentStep = 3,
                totalSteps = 4,
                rating = 5.0f,
                onDismiss = { stopTour() },
                onNextClick = { nextStep() },
                onPreviousClick = { previousStep() }
            )
        ),
        TourComposeStep(
            id = CustomDemoSteps.SWITCH.id.toString(),
            bubbleContentSettings = CustomBubbleContent(
                title = "🎯 Personalización Total",
                description = "¡Increíble! Has visto todo el potencial de personalización. Desde colores básicos hasta componentes completamente únicos. ¡Las posibilidades son infinitas!",
                currentStep = 4,
                totalSteps = 4,
                rating = 4.7f,
                onDismiss = { stopTour() },
                onNextClick = { stopTour() },
                onPreviousClick = { previousStep() }
            )
        )
    )

    companion object {
        const val CUSTOM_DEMO_FLOW = "custom_demo_flow"
    }
}

enum class CustomDemoSteps(val id: UUID, val stepIndex: Int) {
    IMAGE(UUID.randomUUID(), 0),
    BUTTON(UUID.randomUUID(), 1),
    TEXT_FIELD(UUID.randomUUID(), 2),
    SWITCH(UUID.randomUUID(), 3)
}