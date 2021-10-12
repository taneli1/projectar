package com.example.projectar.ui.utils

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.ui.components.ArInterface
import com.example.projectar.ui.functional.ar.intf.ArViewManager
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.ProjectarTheme
import com.google.ar.sceneform.ux.ArFragment

/**
 * Interface used by an Activity to provide additional UI elements for AR-View
 */
interface ArViewUiProvider {
    /** Call to show the UI for AR-View*/
    fun setupInterface(arFragment: ArFragment)

    /** Call to hide the UI for AR-View*/
    fun hideInterface()
}

object ArViewUtils {
    /**
     * Call to set AR UI elements to a ComposeView.
     * @param composeView view to attach the UI to
     * @param arViewManager A Manager for the scene to handle models
     */
    @ExperimentalAnimationApi
    fun attachArHud(
        composeView: ComposeView,
        arViewManager: ArViewManager<Long>,
        viewModel: ProductViewModel,
        navHostController: NavController
    ) {
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                ProjectarTheme {
                    ArInterface(viewModel, arViewManager)
                    FakeBottomBar(
                        destinations = NavUtils.topLevelDest,
                        navController = navHostController
                    )
                }
            }
        }
    }

    fun releaseArHud(composeView: ComposeView) {
        composeView.apply { setContent {} }
    }
}

/**
 * Handle navigation from the fake bottom bar
 */
private fun handleNavigation(navController: NavController, screen: Screen) {
    when (screen.route) {
        Screen.Home.route -> {
            navController.navigate(R.id.action_fragment_ar_view_to_fragment_compose)
        }
        Screen.Search.route -> {
            navController.navigate(R.id.action_fragment_ar_view_to_fragment_compose)
        }
        else -> {

        }
    }
}


/**
 * A Bottom bar specifically made for the AR-Fragment to give the illusion of
 * still having the same bottom bar in that view.
 */
@Composable
private fun FakeBottomBar(
    destinations: List<Screen>,
    navController: NavController,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.height(50.dp)
    ) {

        BottomNavigation(
            backgroundColor = DarkGrey,
            contentColor = Color.White,
        ) {
            destinations.forEach { screen ->
                BottomNavigationItem(
                    label = { Text(text = stringResource(id = screen.resourceId)) },
                    icon = {
                        Icon(
                            painter = painterResource(id = NavUtils.getScreenDrawable(screen)),
                            contentDescription = stringResource(id = R.string.content_desc_placeholder)
                        )
                    },
                    selected = screen.route == Screen.Ar.route, // Always show the Ar view as selected
                    onClick = {
                        handleNavigation(navController, screen)
                    },
                )
            }
        }
    }
}

