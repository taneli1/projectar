package com.example.projectar.ui.components.navigation

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.projectar.R
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.utils.NavUtils
import com.example.projectar.ui.utils.Screen

/**
 * Provides a top bar for the application
 * @param topLevelDestinations Screens which are considered top level
 */
@Composable
fun TopBarWithBurger(
    navController: NavController,
    topLevelDestinations: List<Screen>,
    currentDestination: NavDestination?
) {
    val topLevelRoutes = remember {
        topLevelDestinations.map {
            it.route
        }
    }

    // Show back button if we are not at top level destinations
    val showBackButton =
        currentDestination?.hierarchy?.any { topLevelRoutes.contains(it.route) } != true

    val currentScreenTitle = currentDestination?.hierarchy?.let { seq ->
        NavUtils.getRouteTitle(seq.iterator().next().route ?: "")
    } ?: R.string.content_desc_placeholder

    Bar(navController, showBackButton, stringResource(id = currentScreenTitle))
}

@Composable
private fun Bar(
    navController: NavController,
    withBackIcon: Boolean = false,
    routeTitle: String
) {
    // New top bar needs to be created when a back icon is required, otherwise
    // there is whitespace in the spot of the icon which cant be removed
    if (withBackIcon) {
        TopAppBar(
            title = { Text(routeTitle) },
            backgroundColor = Orange,
            contentColor = Color.White,
            navigationIcon = {
                com.example.projectar.ui.components.common.IconButton(
                    onClick = { navController.popBackStack() },
                    drawableRes = R.drawable.ic_baseline_arrow_back_32
                )
            },
            actions = { BurgerContent(navController) }
        )
    } else {
        TopAppBar(
            title = { Text(routeTitle) },
            backgroundColor = Orange,
            contentColor = Color.White,
            actions = { BurgerContent(navController) }
        )
    }
}

@Composable
private fun BurgerContent(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_menu_24),
            contentDescription = stringResource(id = R.string.menu_burger)
        )
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(onClick = { navController.navigate(Screen.Profile.route) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_person_24),
                contentDescription = stringResource(id = R.string.menu_profile)
            )
            Text(text = stringResource(R.string.profile))
        }
        DropdownMenuItem(onClick = { navController.navigate(Screen.Cart.route) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24),
                contentDescription = stringResource(id = R.string.menu_cart)
            )
            Text(text = stringResource(R.string.cart))
        }
    }
}