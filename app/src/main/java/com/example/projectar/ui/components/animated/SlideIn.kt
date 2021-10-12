package com.example.projectar.ui.components.animated

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Animate component entering/exiting a screen with a horizontal slide.
 * @param visible Should the animated content be visible
 * @param content Composable to animate
 */
@ExperimentalAnimationApi
@Composable
fun AnimateHorizontal(
    visible: MutableState<Boolean>,
    content: @Composable() () -> Unit
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = visible.value,
        enter = slideInHorizontally(
            // Slide in from 40 dp from the top.
            initialOffsetX = { with(density) { -40.dp.roundToPx() } }
        ) + expandHorizontally(
            // Expand from the top.
            expandFrom = Alignment.Start
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
    ) {
        content()
    }
}