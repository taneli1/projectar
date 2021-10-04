package com.example.projectar.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.components.ItemBox
import com.example.projectar.ui.components.TopBarWithBurger


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainList(
    products: List<Product>,
    navigate: (productId: Long) -> Unit,
) {
    Column {
        TopBarWithBurger()
        LazyVerticalGrid(
            cells = GridCells.Fixed(2)
        ) {
            items(products) { Product ->
                ItemBox(Product, navigate)
            }
        }
    }
}