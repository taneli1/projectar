package com.example.projectar.ui.utils

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.projectar.R
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.components.common.CartFAB
import com.example.projectar.ui.components.navigation.NavWrapper
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.screens.*

/**
 * Function to navigate between the fragments in the navGraph.
 * (For this application, ArFragment + ComposeFragment)
 */
typealias NavFunction = (id: Int) -> Unit

// Nav destinations

const val NAV_SINGLE_SCREEN_PARAM = "product"

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.nav_home)
    object Search : Screen("search", R.string.nav_search)
    object Ar : Screen("ar", R.string.nav_ar)
    object Profile : Screen("profile", R.string.nav_profile)
    object Cart : Screen("cart", R.string.nav_cart)
    object Rooms : Screen("rooms", R.string.nav_rooms)
    object SingleProduct : Screen("single/{product}", R.string.nav_single)
}

object NavUtils {

    /** List of top level destinations in the app, made into bottom tabs */
    val topLevelDest = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Ar
    )

    /**
     * Return a res id for a drawable representing a screen
     */
    fun getScreenDrawable(screen: Screen): Int {
        return when (screen) {
            Screen.Home -> R.drawable.ic_baseline_home_24
            Screen.Search -> R.drawable.ic_baseline_search_24
            Screen.Ar -> R.drawable.augmented_reality
            else -> R.drawable.ic_baseline_delete_outline_24
        }
    }

    /**
     * Return a res id for a title of a destination based on a route
     */
    fun getRouteTitle(route: String): Int {
        return when (route) {
            Screen.Home.route -> Screen.Home.resourceId
            Screen.Search.route -> Screen.Search.resourceId
            Screen.Ar.route -> Screen.Ar.resourceId
            Screen.Profile.route -> Screen.Profile.resourceId
            Screen.Cart.route -> Screen.Cart.resourceId
            Screen.Rooms.route -> Screen.Rooms.resourceId
            Screen.SingleProduct.route -> Screen.SingleProduct.resourceId
            else -> R.string.content_desc_placeholder
        }
    }

    /**
     * Creates a navigator for the application, containing all the screens. (except ar)
     */
    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun CreateNavigator(
        navController: NavHostController,
        viewModel: ProductViewModel,
        navigateToFragment: NavFunction
    ) {
        val productList: List<Product> by viewModel.products.observeAsState(listOf())

        /**
         * Creates random lists for home view to use and show
         */
        val initialing = remember {
            mutableStateOf(true)
        }

        val listOfDestinations = topLevelDest

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        NavWrapper(navController = navController, listOfDestinations, currentDestination, {
            FloatingActionButtonCart(
                navController = navController,
                currentDestination = currentDestination,
                cart = viewModel.useCart()
            )
        }) {
            NavHost(navController = navController, startDestination = Screen.Home.route) {

                composable(
                    Screen.SingleProduct.route,
                    arguments = listOf(navArgument(NAV_SINGLE_SCREEN_PARAM) {
                        type = NavType.LongType
                    })
                ) { backStackEntry ->
                    // Get selected item id from navArguments and provide the data for the screen
                    backStackEntry.arguments?.getLong(NAV_SINGLE_SCREEN_PARAM)?.let { id ->
                        productList.find { it.data.id == id }?.let { product ->
                            SingleProduct(product, viewModel, navController, viewModel.useCart())
                        }
                    }
                }

                composable(Screen.Search.route) {
                    MainList(viewModel) {
                        // Navigate to single screen with the clicked product id
                        navController.navigate(
                            Screen.SingleProduct.route.replace(
                                "{$NAV_SINGLE_SCREEN_PARAM}", it.toString()
                            )
                        )
                    }
                }

                composable(Screen.Profile.route) {
                    Profile(viewModel)
                }

                composable(Screen.Home.route) {
                    Home(
                        viewModel = viewModel,
                        navigate =
                        {
                            navController.navigate(
                                Screen.SingleProduct.route.replace(
                                    "{$NAV_SINGLE_SCREEN_PARAM}", it.toString()
                                )
                            )
                        },
                        initialing
                    )
                }

                composable(Screen.Cart.route) {
                    Cart(viewModel, navController)
                }

                composable(Screen.Rooms.route) {
                    Rooms()
                }

                composable(Screen.Ar.route) {
                    navigateToFragment(R.id.fragment_ar_view)
                }
            }
        }
    }

    /**
     * Cart FAB with functionality
     */
    @Composable
    private fun FloatingActionButtonCart(
        navController: NavController,
        currentDestination: NavDestination?,
        cart: Cart
    ) {
        val totalItems: Int by cart.getCartTotal().observeAsState(0)

        val cartButtonHiddenRoutes = listOf(
            Screen.Cart.route,
            Screen.Profile.route,
            Screen.Rooms.route,
            Screen.Ar.route,
            Screen.SingleProduct.route,
        )
        val cartNotEmpty = cart.getAll().value?.isNotEmpty()

        val showFab =
            if (cartButtonHiddenRoutes.contains(currentDestination?.route)) false else cartNotEmpty

        CartFAB(
            onClick = { navController.navigate(Screen.Cart.route) },
            visible = showFab == true,
            itemCount = totalItems
        )
    }
}
