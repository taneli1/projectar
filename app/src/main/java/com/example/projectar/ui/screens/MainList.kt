package com.example.projectar.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.projectar.MainActivity
import com.example.projectar.data.datahandlers.product.ProductManager
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.queryfilters.ProductFilter
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.ui.components.*
import com.example.projectar.ui.viewmodel.ProductViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainList(
    //Todo no need to pass products
    products: List<Product>,
    navController: NavController,
    viewModel: ProductViewModel,
    navigate: (productId: Long) -> Unit
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    fun applyFilter(textState: String) {
        viewModel.applyFilter(ProductFilter(textState))
    }
    Scaffold( topBar = { TopBarWithBurger(navController) },  bottomBar = { BottomBar() }, content = {
        Column {
            SearchView(state = textState, filter = {applyFilter(textState.toString())})
            Log.d("textState", textState.value.text)
            Dropdown()
            LazyVerticalGrid(
                cells = GridCells.Fixed(2)
            ) {
                items(products) { Product ->
                    ItemBox(Product, navigate)
                }
            }
        }
    })
}