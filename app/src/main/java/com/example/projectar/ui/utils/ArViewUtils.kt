package com.example.projectar.ui.utils

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.projectar.ui.components.ArInterface
import com.example.projectar.ui.functional.ar.ArViewManager
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.ProjectarTheme
import com.google.ar.sceneform.ux.ArFragment

/**
 * Interface used by MainActivity to provide required UI for AR-View
 */
interface ArViewUiProvider {
    /** Call to show the UI for AR-View*/
    fun setupInterface(arFragment: ArFragment)

    /** Call to hide the UI for AR-View*/
    fun hideInterface()
}

object ArViewUtils {
    /**
     * Call to set AR UI hud to a ComposeView.
     * @param composeView view to attach the UI to
     * @param arViewManager A Manager for the scene to handle objects
     */
    @ExperimentalAnimationApi
    fun attachArHud(
        composeView: ComposeView,
        arViewManager: ArViewManager,
        viewModel: ProductViewModel,
    ) {
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProjectarTheme {
                    ArInterface(viewModel, arViewManager)
                }
            }
        }
    }

    fun releaseArHud(composeView: ComposeView) {
        composeView.apply { setContent {} }
    }
}
