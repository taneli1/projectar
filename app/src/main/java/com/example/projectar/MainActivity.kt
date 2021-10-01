package com.example.projectar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.utils.ProductCreator
import com.example.projectar.di.Injector
import com.example.projectar.ui.screens.MainList
import com.example.projectar.ui.screens.SingleProduct
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.theme.darkGrey
import com.example.projectar.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val db by lazy { ApplicationDatabase.get(this) }

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
                        SingleProduct(product = product, navController)
                    }
                }
            }
            composable("testList") {
                MainList(data) {
                    navigate(
                        navController,
                        "singleProduct/$it"
                    )
                }
            }
        }
    }
}

fun navigate(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo("testBox") { inclusive = true }
    }
}