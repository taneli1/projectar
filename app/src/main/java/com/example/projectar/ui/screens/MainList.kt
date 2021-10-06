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


    val orderingOptions = listOf("Alphabetical", "Price")
    var trueProductList = products

    fun doOrdering(ordering: String) {
        if (ordering == "Alphabetical") {
            trueProductList = trueProductList.sortedBy { it.data.id }

        } else if (ordering == "Price") {
            trueProductList = trueProductList.sortedBy { it.data.price }
            for (Product: Product in trueProductList) {
                Log.d("Prices", Product.data.price.toString())
            }
        }
    }

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    fun applyFilter(textState: String, tags: MutableList<ProductTag>) {
        Log.d("APPLIED TAGS: ", selectedItems.toString())
        viewModel.applyFilter(ProductFilter(textState, tags))
    }

    Column {
        SearchView(
            state = textState,
            filter = { applyFilter(textState.toString(), selectedItems) })
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Dropdown(
                items,
                selectedItems,
                viewModel,
                filters = { applyFilter(textState.toString(), selectedItems) })
            OrderingDropdown(orderingOptions, ordering = ::doOrdering)
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
        ) {
            items(trueProductList) { Product ->
                ItemBox(Product, navigate)
            }
        }
    }

}