package com.example.projectar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.projectar.di.Injector
import com.example.projectar.ui.screens.MainList
import com.example.projectar.ui.screens.SingleProduct
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.theme.darkGrey
import com.example.projectar.ui.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy { ApplicationDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectarTheme {
                SetUp()
                // TestComposable.TestScreen(db)
            }
        }
    }

    @Composable
    fun SetUp() {
        val viewModel: ProductViewModel = viewModel(
            factory = Injector.provideProductViewModelFactory(db)
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

// Work in progress
/*
@Composable
fun BurgerTopAppBar(
    //content: RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = Orange)
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(text = "hello")

    }
}

*/

/*
@Composable
fun FriendsList(navController: NavController) {
    /*...*/
    Button(onClick = {
        navController.navigate("profile") {
            popUpTo("home") { inclusive = true }
        }
    }) {
        Text(text = "Navigate next")
    }
    /*...*/
}
*/