package com.example.projectar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
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
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.theme.darkGrey
import com.example.projectar.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//data class Product(val first: String, val second: String, val img: Int)
//val data = mutableListOf<Product>()

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
                        SingleProduct(product = product)
                    }
                }
            }
            composable("testList") {
                TestList(viewModel, data) {
                    navigate(
                        navController,
                        "singleProduct/$it"
                    )
                }
            }
        }
    }
}

private fun navigate(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo("testBox") { inclusive = true }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestList(
    viewModel: ProductViewModel,
    products: List<Product>,
    navigate: (productId: Long) -> Unit
) {


    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(products) { product ->
            product.image?.let {
                TestBox(product, navigate, viewModel.getImage(it).asImageBitmap())
            }
        }
    }
}

@Composable
fun TestBox(msg: Product, navigate: (productId: Long) -> Unit, imageBitmap: ImageBitmap) {
    Column(
        Modifier
            .padding(10.dp)
            .clip(Shapes.medium)
            .selectable(selected = true, onClick = { navigate(msg.data.id) })
    ) {
        Image(
            imageBitmap,
            contentDescription = "picture",
            contentScale = Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(darkGrey)
                .padding(5.dp)
        ) {
            Text(text = msg.data.title, color = Color.White)
            Text(text = msg.data.price.toString(), color = Color.White)
        }
    }
}

@Composable
fun SingleProduct(product: Product) {
    Column(
        Modifier
            .padding(10.dp)
            .clip(Shapes.medium)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(darkGrey)
                .padding(5.dp)
        ) {
            Text(text = product.data.title, color = Color.White)
            Text(text = "msg.second", color = Color.White)
        }
    }
    Log.d("HERERERE#", "we got here with id: " + product.data.id)
}
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

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectarTheme {
        TestList(data)
    }
}*/