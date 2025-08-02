package com.tonyakitori.apps.tourcomposedemo

import com.tonyakitori.apps.tourcompose.controller.TourComposeController
import com.tonyakitori.apps.tourcompose.controller.TourComposeStep
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.bubbleContentBasicSettings
import com.tonyakitori.apps.tourcompose.settings.colors.BubbleContentColors
import java.util.UUID

class TourComposeAppController(
    private val useCustomBubbleContent: Boolean = false,
    private val bubbleContentColors: BubbleContentColors? = null
) : TourComposeController() {

    init {

        addTour(
            flowId = TITLE_AND_IMAGE_FLOW,
            steps = if (useCustomBubbleContent) {
                createCustomTitleAndImageSteps()
            } else {
                createBasicTitleAndImageSteps()
            }
        )

        addTour(
            flowId = COMPLETE_TOUR_COMPOSE_FLOW,
            steps = listOf(
                TourComposeStep(
                    id = CompleteTourComposeSteps.CHANGE_THEME.id.toString(),
                    bubbleContentSettings = bubbleContentBasicSettings(
                        title = "Change the theme",
                        description = "With this button you can change the theme",
                        primaryButtonText = "Next",
                        secondaryButtonText = null,
                        onPrimaryClick = {
                            nextStep()
                        },
                        onDismiss = {
                            stopTour()
                        }
                    )
                ),
                TourComposeStep(
                    id = CompleteTourComposeSteps.SIMPLE_IMAGE.id.toString(),
                    bubbleContentSettings = bubbleContentBasicSettings(
                        title = "A Simple Image",
                        description = "This is a simple image that composes the screen",
                        primaryButtonText = "Next",
                        secondaryButtonText = "Back",
                        onPrimaryClick = {
                            nextStep()
                        },
                        onSecondaryClick = {
                            previousStep()
                        },
                        onDismiss = {
                            stopTour()
                        }
                    )
                ),
                TourComposeStep(
                    id = CompleteTourComposeSteps.AN_INPUT.id.toString(),
                    bubbleContentSettings = bubbleContentBasicSettings(
                        title = "An Input",
                        description = "This is an input",
                        primaryButtonText = "Next",
                        secondaryButtonText = "Back",
                        onPrimaryClick = {
                            nextStep()
                        },
                        onSecondaryClick = {
                            previousStep()
                        },
                        onDismiss = {
                            stopTour()
                        }
                    )
                ),
                TourComposeStep(
                    id = CompleteTourComposeSteps.A_SWITCH.id.toString(),
                    bubbleContentSettings = bubbleContentBasicSettings(
                        title = "A Switch",
                        description = "This is a switch",
                        primaryButtonText = "Finish",
                        secondaryButtonText = "Back",
                        onPrimaryClick = {
                            stopTour()
                        },
                        onSecondaryClick = {
                            previousStep()
                        },
                        onDismiss = {
                            stopTour()
                        }
                    )
                )

            )
        )

    }

    private fun createBasicTitleAndImageSteps(): List<TourComposeStep> = listOf(
        TourComposeStep(
            id = TitleAndImageSteps.TITLE.id.toString(),
            bubbleContentSettings = bubbleContentBasicSettings(
                title = "Este es el titulo",
                description = "En el titulo esta el detalle de todo",
                primaryButtonText = "Siguiente",
                secondaryButtonText = null,
                colors = bubbleContentColors,
                onPrimaryClick = {
                    nextStep()
                },
                onDismiss = {
                    stopTour()
                }
            )
        ),
        TourComposeStep(
            id = TitleAndImageSteps.IMAGE.id.toString(),
            bubbleContentSettings = bubbleContentBasicSettings(
                title = "Una imagen",
                description = "Esta es una imagen que compone la pantalla",
                primaryButtonText = "Finalizar",
                secondaryButtonText = "Atras",
                colors = bubbleContentColors,
                onPrimaryClick = {
                    stopTour()
                },
                onSecondaryClick = {
                    previousStep()
                },
                onDismiss = {
                    stopTour()
                }
            )
        )
    )

    private fun createCustomTitleAndImageSteps(): List<TourComposeStep> = listOf(
        TourComposeStep(
            id = TitleAndImageSteps.TITLE.id.toString(),
            bubbleContentSettings = customBubbleContent(
                title = "🎯 Tutorial Interactivo",
                description = "Bienvenido al tour personalizado con progreso visual y rating interactivo",
                currentStep = 1,
                totalSteps = 2,
                rating = 4.8f,
                onNextClick = {
                    nextStep()
                },
                onPreviousClick = {
                    // No hay paso anterior
                },
                onDismiss = {
                    stopTour()
                }
            )
        ),
        TourComposeStep(
            id = TitleAndImageSteps.IMAGE.id.toString(),
            bubbleContentSettings = customBubbleContent(
                title = "🖼️ Elementos Visuales",
                description = "Esta imagen es un componente clave de nuestra interfaz, diseñada para captar la atención del usuario",
                currentStep = 2,
                totalSteps = 2,
                rating = 4.9f,
                onNextClick = {
                    stopTour()
                },
                onPreviousClick = {
                    previousStep()
                },
                onDismiss = {
                    stopTour()
                }
            )
        )
    )

    companion object {
        const val TITLE_AND_IMAGE_FLOW = "title_and_image"
        const val COMPLETE_TOUR_COMPOSE_FLOW = "complete_tour_compose"
    }
}

enum class TitleAndImageSteps(val id: UUID, val stepIndex: Int) {
    TITLE(UUID.randomUUID(), 0),
    IMAGE(UUID.randomUUID(), 1)
}

enum class CompleteTourComposeSteps(val id: UUID, val stepIndex: Int) {
    CHANGE_THEME(UUID.randomUUID(), 0),
    SIMPLE_IMAGE(UUID.randomUUID(), 1),
    AN_INPUT(UUID.randomUUID(), 2),
    A_SWITCH(UUID.randomUUID(), 3)
}