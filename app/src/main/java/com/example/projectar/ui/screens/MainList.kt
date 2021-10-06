package com.example.projectar.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.queryfilters.ProductFilter
import com.example.projectar.data.room.queryfilters.SortBy
import com.example.projectar.data.utils.TagUtils
import com.example.projectar.ui.components.Dropdown
import com.example.projectar.ui.components.ItemBox
import com.example.projectar.ui.components.OrderingDropdown
import com.example.projectar.ui.components.SearchView
import com.example.projectar.ui.functional.viewmodel.ProductViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainList(
    //Todo no need to pass products
    products: List<Product>,
    navController: NavController,
    viewModel: ProductViewModel,
    navigate: (productId: Long) -> Unit
) {
    val items = TagUtils.getAllTags()
    val selectedItems: MutableList<ProductTag> = remember {
        items.toMutableList()
    }

    val orderingOptions = mutableMapOf<String, SortBy>()
    orderingOptions["Default"] = SortBy.DEFAULT
    orderingOptions["Price Ascending"] = SortBy.PRICE_ASC
    orderingOptions["Price Descending"] = SortBy.PRICE_DESC
    orderingOptions["Alphabetical Ascending"] = SortBy.ALPHABETICAL_ASC
    orderingOptions["Alphabetical Descending"] = SortBy.ALPHABETICAL_DESC
    val selectedSorting = remember { mutableStateOf(orderingOptions["Price Ascending"]) }


    val textState = remember { mutableStateOf(TextFieldValue("")) }
    fun applyFilter(textState: String, tags: MutableList<ProductTag>, sortBy: SortBy) {
        Log.d("KEY HERE", sortBy.toString())
        viewModel.applyFilter(ProductFilter(textState, tags, sortBy))
    }
    
        Column {
            SearchView(
                state = textState,
                filter = {
                    selectedSorting.value?.let { it1 ->
                        applyFilter(textState.toString(), selectedItems,
                            it1
                        )
                    }
                })
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Dropdown(
                    items,
                    selectedItems,
                    viewModel,
                    filters = {
                        selectedSorting.value?.let { it1 ->
                            applyFilter(textState.toString(), selectedItems,
                                it1
                            )
                        }
                    })
                OrderingDropdown(orderingOptions, selectedSorting) {
                    selectedSorting.value?.let { it1 ->
                        applyFilter(
                            textState.toString(),
                            selectedItems,
                            it1
                        )
                    }
                }
            }
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
            ) {
                items(products) { Product ->
                    ItemBox(Product, navigate)
                }
            }
        }
}