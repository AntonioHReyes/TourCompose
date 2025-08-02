package com.tonyakitori.apps.tourcompose.controller

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import com.tonyakitori.apps.tourcompose.settings.bubbleContent.BubbleContentSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

data class TourComposeStep(
    val id: String = UUID.randomUUID().toString(),
    val bubbleContentSettings: BubbleContentSettings,
    var componentRect: Rect? = null,
)

@OptIn(ExperimentalCoroutinesApi::class)
abstract class TourComposeController {

    private val flows: MutableMap<String, List<TourComposeStep>> = mutableMapOf()

    private val _currentStepIndex = MutableSharedFlow<Int>(replay = 1)
    private val currentStepIndex: SharedFlow<Int> = _currentStepIndex

    val currentStep: Flow<TourComposeStep?> = currentStepIndex.flatMapLatest { index ->
        _currentFlowId?.let { flowId ->
            flows[flowId]?.getOrNull(index)
        }?.let { flowOf(it) } ?: flowOf(null)
    }

    private var _currentFlowId: String? = null
    val currentFlowId: String? get() = _currentFlowId

    open fun onTourStart(flowId: String) {
        Log.i("TourComposeController","onTourStart: $flowId")
    }

    open fun onTourEnd(flowId: String) {
        Log.i("TourComposeController","onTourEnd: $flowId")
    }

    open fun startTour(flowId: String) {
        _currentFlowId = flowId
        _currentStepIndex.tryEmit(0)
        onTourStart(flowId)
    }

    open fun nextStep() {
        val currentTourSteps = _currentFlowId?.let { flows[it] }
        val currentIndex = _currentStepIndex.replayCache.lastOrNull() ?: -1
        if (currentIndex < (currentTourSteps?.size ?: 1) - 1) {
            _currentStepIndex.tryEmit(currentIndex + 1)
        } else {
            stopTour()
        }
    }

    open fun resumeTour() {
        val currentTourSteps = _currentFlowId?.let { flows[it] }
        val currentIndex = _currentStepIndex.replayCache.lastOrNull() ?: -1
        if (currentIndex < (currentTourSteps?.size ?: 1) - 1) {
            _currentStepIndex.tryEmit(currentIndex)
        }
    }

    open fun previousStep() {
        val currentIndex = _currentStepIndex.replayCache.lastOrNull() ?: -1
        if (currentIndex > 0) {
            _currentStepIndex.tryEmit(currentIndex - 1)
        }
    }

    open fun stopTour() {
        _currentFlowId?.let { onTourEnd(it) }
        _currentStepIndex.tryEmit(-1)
        _currentFlowId = null
    }

    fun addTour(flowId: String, steps: List<TourComposeStep>) {
        flows[flowId] = steps
    }

    fun removeTour(flowId: String) {
        flows.remove(flowId)
    }

    fun addLayoutRect(
        flowId: String,
        step: Int,
        rect: Rect
    ) {
        flows[flowId]?.get(step)?.componentRect = rect
    }
}

val LocalTourController = compositionLocalOf<TourComposeController> {
    error("TutorialController not found")
}

interface TourComposeScope {
    @Stable
    fun Modifier.tourStepIndex(flowId: String, step: Int): Modifier
}

class TourComposeScopeImpl(private val tutorialController: TourComposeController) :
    TourComposeScope {

    @Stable
    override fun Modifier.tourStepIndex(flowId: String, step: Int) =
        this.onGloballyPositioned { layoutCoordinates ->
            tutorialController.addLayoutRect(flowId, step, layoutCoordinates.boundsInRoot())
        }
}

@Composable
fun TourComposeWrapper(
    tourController: TourComposeController,
    content: @Composable TourComposeScopeImpl.() -> Unit
) {
    CompositionLocalProvider(
        value = LocalTourController provides tourController
    ) {
        TourComposeScopeImpl(tourController).content()
    }
}