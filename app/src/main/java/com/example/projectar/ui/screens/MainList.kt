package com.example.projectar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.projectar.R
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


@Composable
fun MainList(
    viewModel: ProductViewModel,
    navigate: (productId: Long) -> Unit
) {
    val items = TagUtils.getAllTags()
    val selectedItems: MutableList<ProductTag> = remember {
        viewModel.getFilter().tags.toMutableList()
    }
    val products: List<Product> by viewModel.products.observeAsState(listOf())


    val orderingOptions = mutableMapOf<SortBy, String>()
    orderingOptions[SortBy.DEFAULT] = "Default"
    orderingOptions[SortBy.PRICE_ASC] = "Price Ascending"
    orderingOptions[SortBy.PRICE_DESC] = "Price Descending"
    orderingOptions[SortBy.ALPHABETICAL_ASC] = "Alphabetical Ascending"
    orderingOptions[SortBy.ALPHABETICAL_DESC] = "Alphabetical Descending"
    val selectedSorting = remember { mutableStateOf(viewModel.getFilter().sortBy) }


    val searchTerm = viewModel.getFilter().searchTerm
    val textState = remember { mutableStateOf(TextFieldValue(searchTerm)) }
    fun applyFilter(textState: String, tags: MutableList<ProductTag>, sortBy: SortBy) {
        viewModel.applyFilter(ProductFilter(textState, tags, sortBy))
    }

    @Composable
    fun Header() {
        SearchView(
            state = textState,
            filter = {
                applyFilter(
                    textState.value.text,
                    selectedItems,
                    selectedSorting.value
                )
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
                    applyFilter(
                        textState.value.text,
                        selectedItems,
                        selectedSorting.value
                    )
                })
            OrderingDropdown(orderingOptions, selectedSorting) {
                applyFilter(
                    textState.value.text,
                    selectedItems,
                    selectedSorting.value
                )
            }
        }
        Text(
            text = stringResource(id = R.string.products_matching_filter, products.count()),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
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
                        ProductRowWrapper(
                            products = listOf(products[index]),
                            navigate = navigate,
                            viewModel
                        )
                    } else {
                        val pair = listOf(products[index], products[index + 1])
                        ProductRowWrapper(products = pair, navigate = navigate, viewModel)
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
fun ProductRowWrapper(
    products: List<Product>,
    navigate: (productId: Long) -> Unit,
    viewModel: ProductViewModel
) {
    Row(Modifier.fillMaxWidth()) {
        products.forEachIndexed { index, product ->
            // Calculate the width percentage that the box can take
            val percentage = (100f / (products.size - index)) / 100f
            Surface(Modifier.fillMaxWidth(percentage)) {
                ItemBox(product, navigate = navigate, viewModel = viewModel)
            }
        }
    }
}

