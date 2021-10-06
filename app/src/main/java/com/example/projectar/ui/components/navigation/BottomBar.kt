package com.example.projectar.ui.components.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.projectar.R
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.utils.NavUtils
import com.example.projectar.ui.utils.Screen

const val LABEL_HOME = "Home"
const val LABEL_SEARCH = "Search"
const val LABEL_AR = "Ar"

/**
 * Bottom bar
 */
@Composable
fun BottomBar(
    navController: NavController,
    destinations: List<Screen>,
    currentDestination: NavDestination?
) {
    BottomNavigation(
        backgroundColor = DarkGrey,
        contentColor = Color.White
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
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },

                )
        }
    }
}