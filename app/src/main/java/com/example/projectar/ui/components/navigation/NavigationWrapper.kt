package com.example.projectar.ui.components.navigation

import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.projectar.ui.utils.Screen

/**
 * Wrap a component with navigation components. (Top + Bottom bars)
 * @param topLevelDestinations Screens witch are top level, made into bottom nav bar buttons
 */
@Composable
fun NavWrapper(
    navController: NavController,
    topLevelDestinations: List<Screen>,
    currentDestination: NavDestination?,
    floatingActionButton: @Composable() () -> Unit,
    content: @Composable() () -> Unit,
) {

    Scaffold(
        topBar = {
            TopBarWithBurger(
                navController = navController,
                topLevelDestinations,
                currentDestination
            )
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = { BottomBar(navController, topLevelDestinations, currentDestination) },
        content = { content() }
    )
}