package com.example.projectar.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectar.R
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.utils.TagUtils
import com.example.projectar.ui.components.ItemBox
import com.example.projectar.ui.functional.viewmodel.ProductViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    products: List<Product>,
    viewModel: ProductViewModel,
    filteredProducts: List<Product>,
    filteredProducts1: List<Product>,
    randomizedList: List<Product>,
    tag: ProductTag,
    tag1: ProductTag,
    navigate: (productId: Long) -> Unit
) {

    //TODO empty array will crash the app

    if (randomizedList.isNotEmpty()) {
        LazyColumn(Modifier.padding(bottom = 50.dp)) {
            item() {
                Text(
                    modifier = Modifier.padding(20.dp),
                    fontSize = 30.sp,
                    text = stringResource(R.string.Randompicksforyou)
                )
                Row() {
                    ItemBox(
                        product = randomizedList[0],
                        width = 0.5f,
                        navigate = navigate
                    )
                    ItemBox(
                        product = randomizedList[1],
                        width = 1.0f,
                        navigate = navigate,
                    )
                }
                Row() {
                    ItemBox(
                        product = randomizedList[2],
                        width = 0.5f,
                        navigate = navigate
                    )
                    ItemBox(
                        product = randomizedList[3],
                        width = 1.0f,
                        navigate = navigate,
                    )
                }
            }
            item() {
                Text(
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    text = "Random picks from category $tag"
                )
                Row(
                    Modifier.horizontalScroll(
                        enabled = true,
                        flingBehavior = null,
                        reverseScrolling = false,
                        state = ScrollState(initial = 1)
                    )
                ) {
                    for (item in filteredProducts) {
                        ItemBox(product = item, width = 1.0f, navigate = navigate)
                    }
                }
            }
            item() {
                Text(
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    text = "Random picks from category $tag1"
                )
                Row(
                    Modifier.horizontalScroll(
                        enabled = true,
                        flingBehavior = null,
                        reverseScrolling = false,
                        state = ScrollState(initial = 1)
                    )
                ) {
                    for (item in filteredProducts1) {
                        ItemBox(product = item, width = 1.0f, navigate = navigate)
                    }
                }
            }
        }
    }
}