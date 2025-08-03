package com.tonyakitori.apps.tourcomposedemo.tabs.basicdemo

import com.tonyakitori.apps.tourcompose.controller.TourComposeController
import com.tonyakitori.apps.tourcompose.controller.TourComposeStep
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.bubbleContentBasicSettings
import com.tonyakitori.apps.tourcompose.settings.colors.BubbleContentColors
import java.util.UUID

/**
 * Specific controller for basic TourCompose demo.
 * Handles tours using basic components without specific design system dependencies.
 *
 * Note: Tour step texts are kept in Spanish as a tribute to the native language of the developer.
 */
class BasicDemoController(
    private val customColors: BubbleContentColors? = null
) : TourComposeController() {

    init {
        addTour(
            flowId = BASIC_DEMO_FLOW,
            steps = createBasicDemoSteps()
        )
    }

    private fun createBasicDemoSteps(): List<TourComposeStep> = listOf(
        TourComposeStep(
            id = BasicDemoSteps.IMAGE.id.toString(),
            bubbleContentSettings = bubbleContentBasicSettings(
                title = "🖼️ Imagen Básica",
                description = "Esta es una demostración de TourCompose básico sin dependencias de Material3. Usa colores simples y componentes agnósticos.",
                primaryButtonText = "Siguiente",
                secondaryButtonText = null,
                colors = customColors,
                onPrimaryClick = { nextStep() },
                onDismiss = { stopTour() }
            )
        ),
        TourComposeStep(
            id = BasicDemoSteps.BUTTON.id.toString(),
            bubbleContentSettings = bubbleContentBasicSettings(
                title = "🔘 Botón de Inicio",
                description = "Este botón inicia el tour básico. Observa cómo los colores son simples y no dependen de ningún tema específico.",
                primaryButtonText = "Siguiente",
                secondaryButtonText = "Anterior",
                colors = customColors,
                onPrimaryClick = { nextStep() },
                onSecondaryClick = { previousStep() },
                onDismiss = { stopTour() }
            )
        ),
        TourComposeStep(
            id = BasicDemoSteps.TEXT_FIELD.id.toString(),
            bubbleContentSettings = bubbleContentBasicSettings(
                title = "📝 Campo de Texto",
                description = "Los campos de texto funcionan perfectamente con TourCompose básico. La librería es completamente agnóstica al sistema de diseño.",
                primaryButtonText = "Siguiente",
                secondaryButtonText = "Anterior",
                colors = customColors,
                onPrimaryClick = { nextStep() },
                onSecondaryClick = { previousStep() },
                onDismiss = { stopTour() }
            )
        ),
        TourComposeStep(
            id = BasicDemoSteps.SWITCH.id.toString(),
            bubbleContentSettings = bubbleContentBasicSettings(
                title = "🔄 Switch Final",
                description = "Este es el último paso del tour básico. TourCompose básico es perfecto para proyectos que no usan Material3 o quieren máximo control sobre los estilos.",
                primaryButtonText = "Finalizar",
                secondaryButtonText = "Anterior",
                colors = customColors,
                onPrimaryClick = { stopTour() },
                onSecondaryClick = { previousStep() },
                onDismiss = { stopTour() }
            )
        )
    )

    companion object {
        const val BASIC_DEMO_FLOW = "basic_demo_flow"
    }
}

enum class BasicDemoSteps(val id: UUID, val stepIndex: Int) {
    IMAGE(UUID.randomUUID(), 0),
    BUTTON(UUID.randomUUID(), 1),
    TEXT_FIELD(UUID.randomUUID(), 2),
    SWITCH(UUID.randomUUID(), 3)
}