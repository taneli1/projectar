package com.example.projectar.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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
import com.example.projectar.ui.theme.PADDING_MD


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainList(
    products: List<Product>,
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
        viewModel.applyFilter(ProductFilter(textState, tags, sortBy))
    }


    @Composable
    fun Header() {
        SearchView(
            state = textState,
            filter = {
                selectedSorting.value?.let { it1 ->
                    applyFilter(
                        textState.toString(), selectedItems,
                        it1
                    )
                }
            })
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = PADDING_MD, end = PADDING_MD, bottom = PADDING_MD)
        ) {
            Dropdown(
                items,
                selectedItems,
                filters = {
                    selectedSorting.value?.let { it1 ->
                        applyFilter(
                            textState.toString(), selectedItems,
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
    }


    LazyColumn() {
        item {
            Header()
        }
        itemsIndexed(products) { index, _ ->
            val even = index % 2 == 0
            val isLastProduct = index == products.lastIndex

            when (even) {
                true -> {
                    if (isLastProduct) {
                        ProductRowWrapper(products = listOf(products[index]), navigate = navigate)
                    } else {
                        val pair = listOf(products[index], products[index + 1])
                        ProductRowWrapper(products = pair, navigate = navigate)
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
private fun ProductRowWrapper(
    products: List<Product>,
    navigate: (productId: Long) -> Unit,
    addExtra: Int = 0
) {
    Row(Modifier.fillMaxWidth()) {
        products.forEachIndexed { index, product ->
            // Calculate the width percentage that the box can take
            val percentage = (100f / (products.size - index)) / 100f
            Surface(Modifier.fillMaxWidth(percentage)) {
                ItemBox(product, navigate = navigate)
            }
        }
    }
}

