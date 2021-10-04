package com.example.projectar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectar.data.datahandlers.cart.CartImpl
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.utils.ProductCreator
import com.example.projectar.di.Injector
import com.example.projectar.ui.screens.MainList
import com.example.projectar.ui.screens.Profile
import com.example.projectar.ui.screens.SingleProduct
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy { ApplicationDatabase.get(this) }
    private val trueCart = CartImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectarTheme {
                SetUp()
                Row() {
                    Button(onClick = { ProductCreator.nuke(db) }) {
                        Text(text = "Nuke")
                    }
                    Button(onClick = { ProductCreator.createProducts(db) }) {
                        Text(text = "Create")
                    }
                }
                // TestComposable.TestScreen(db)
            }
        }
    }

    @Composable
    fun SetUp() {
        val viewModel: ProductViewModel = viewModel(
            factory = Injector.provideProductViewModelFactory(db, applicationContext)
        )
        val data: List<Product> by viewModel.products.observeAsState(listOf())

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "testList") {
            composable(
                "singleProduct/{product}",
                arguments = listOf(navArgument("product") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getLong("product")?.let { json ->
                    val product = data.find { it.data.id == json }
                    if (product != null) {
                        SingleProduct(product = product, navController =  navController, trueCart = trueCart)
                    }
                }
            }
            composable("testList") {
                MainList(data, navController) {
                    navigate(
                        navController,
                        "singleProduct/$it"
                    )
                }
            }

            //Navigation to profile, doesn't work yet
            composable("profile") {
                Profile(navController)
            }
        }
    }
}

fun navigate(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo("testBox") { inclusive = true }
    }
}