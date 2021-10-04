package com.example.projectar.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.screens.MainList
import com.example.projectar.ui.screens.Profile
import com.example.projectar.ui.screens.SingleProduct
import com.example.projectar.ui.viewmodel.ProductViewModel

typealias NavFunction = (id: Int) -> Unit


object NavUtils {
    /** Navigate to destination, pops everything from backstack? */
    fun navigate(navController: NavController, route: String) {
        navController.navigate(route) {
            popUpTo("testBox") { inclusive = true }
        }
    }

    @Composable
    fun CreateNavigator(navC: NavHostController, viewModel: ProductViewModel) {
        val data: List<Product> by viewModel.filteredProducts.observeAsState(listOf())

        NavHost(navController = navC, startDestination = "testList") {
            composable(
                "singleProduct/{product}",
                arguments = listOf(navArgument("product") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getLong("product")?.let { json ->
                    val product = data.find { it.data.id == json }
                    if (product != null) {
                        SingleProduct(product = product, navC, viewModel.getCart())
                    }
                }
            }
            composable("testList") {
                MainList(data, navC) {
                    navigate(
                        navC,
                        "singleProduct/$it"
                    )
                }
            }
            composable("profile") {
                Profile(navC)
            }
        }
    }
}