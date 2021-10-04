package com.example.projectar.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
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
    fun attachArHud(composeView: ComposeView, viewModel: ProductViewModel) {
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProjectarTheme {
                    Row() {
                        Button(
                            onClick = { /*TODO*/ }) {
                            Text(text = "${viewModel.products.value?.size}")
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
