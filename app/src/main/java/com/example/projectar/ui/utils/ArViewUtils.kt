package com.example.projectar.ui.utils

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.ui.components.ArInterface
import com.example.projectar.ui.functional.ar.ArViewManager
import com.example.projectar.ui.theme.ProjectarTheme
import com.google.ar.sceneform.ux.ArFragment

/**
 * Interface used by MainActivity to provide required UI for AR-View
 */
interface ArViewUiProvider {
    /** Call to show the UI HUD*/
    fun setupInterface(arFragment: ArFragment)

    /** Call to hide the UI HUD*/
    fun hideInterface()
}

object ArViewUtils {
    /**
     * Call to set AR UI hud to a ComposeView.
     */
    fun attachArHud(
        composeView: ComposeView,
        arViewManager: ArViewManager,
        db: ApplicationDatabase
    ) {
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProjectarTheme {
                    ArInterface(arViewManager = arViewManager, db = db)
                }
            }
        }
    }

    fun releaseArHud(composeView: ComposeView) {
        composeView.apply { setContent {} }
    }
}
