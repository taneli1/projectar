package com.example.projectar.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import com.example.projectar.ui.theme.ProjectarTheme

/**
 * Interface used by MainActivity to provide required UI for AR-View
 */
interface ArViewUiProvider {
    /**
     * Call to show the UI HUD
     */
    fun showInterface()

    /**
     * Call to hide the UI HUD
     */
    fun hideInterface()
}

object ArViewUtils {

    /**
     * Call to set AR UI hud to a ComposeView.
     */
    fun applyArHud(composeView: ComposeView) {
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProjectarTheme {
                    Row() {
                        Button(
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp),
                            onClick = { /*TODO*/ }) {
                            Text(text = "`TEst")
                        }
                    }
                }
            }
        }
    }

    fun releaseArHud(composeView: ComposeView) {
        composeView.apply {
            setContent {
            }
        }
    }
}
