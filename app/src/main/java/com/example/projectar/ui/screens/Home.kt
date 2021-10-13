package com.example.projectar.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectar.R
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.components.BigItem
import com.example.projectar.ui.components.ItemBoxForScroll
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.Orange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    viewModel: ProductViewModel,
    navigate: (productId: Long) -> Unit,
    initialing: MutableState<Boolean> = mutableStateOf(false)
) {
    val scope = rememberCoroutineScope()

    val count: MutableState<List<Unit>> = remember {
        mutableStateOf(List(12) {})
    }

    LaunchedEffect(true) {
        scope.launch {
            delay(1500)
            initialing.value = false
        }
    }

    LazyColumn(Modifier.padding(bottom = 50.dp)) {
        item {
            Spacer(modifier = Modifier.size(30.dp))
        }
        itemsIndexed(count.value) { index, _ ->
            RandomLayout(
                "LayoutId$index",
                index,
                viewModel,
                navigate
            )
        }

        item {
            Spacer(modifier = Modifier.size(20.dp))
        }
    }

    /**
     * Show a loading indicator on first load
     */
    if (initialing.value) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = Orange)
            }
        }
    }

}

@Composable
private fun RandomLayout(
    layoutId: String,
    index: Int,
    viewModel: ProductViewModel,
    navigate: (productId: Long) -> Unit,
) {
//    when {
//        index % 3 == 0 -> {
//            Grid(layoutId = layoutId, viewModel = viewModel, navigate = navigate)
//        }
//        index % 2 == 0 -> {
//            SingleLayout(layoutId = layoutId, viewModel = viewModel, navigate = navigate)
//        }
//        else -> {
//            HorizontalList(layoutId = layoutId, viewModel = viewModel, navigate = navigate)
//        }
//    }

    Spacer(modifier = Modifier.size(20.dp))
}

/**
 * Pass in a unique constant layoutId to have the same product list between renders
 */
@Composable
private fun Grid(
    layoutId: String, viewModel: ProductViewModel, navigate: (productId: Long) -> Unit
) {
    val products: List<Product> by viewModel.getRandomListOfProducts(
        layoutId, 2
    ).first.observeAsState(listOf())

    val products2: List<Product> by viewModel.getRandomListOfProducts(
        layoutId + "2", 2
    ).first.observeAsState(listOf())

    Text(
        modifier = Modifier.padding(bottom = 10.dp, start = 14.dp, end = 14.dp),
        fontSize = 28.sp,
        text = stringResource(R.string.pics)
    )

    Column() {
        ProductRowWrapper(products = products, navigate = navigate, viewModel = viewModel)
        ProductRowWrapper(products = products2, navigate = navigate, viewModel = viewModel)
    }
}


@Composable
private fun HorizontalList(
    layoutId: String, viewModel: ProductViewModel, navigate: (productId: Long) -> Unit
) {
    val products: List<Product> by viewModel.getRandomListOfProducts(
        layoutId, 6, true
    ).first.observeAsState(listOf())

    val tagUsed: ProductTag? = viewModel.getRandomListOfProducts(
        layoutId, 6, true
    ).second.firstOrNull()


    if (tagUsed != null)
        Text(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 14.dp),
            fontSize = 24.sp,
            text = stringResource(id = tagUsed.resourceStringId())
        )

    if (products.isNotEmpty())
        Row(
            Modifier.horizontalScroll(
                enabled = true,
                flingBehavior = null,
                reverseScrolling = false,
                state = ScrollState(initial = 1)
            )
        ) {
            for (item in products) {
                ItemBoxForScroll(product = item, viewModel, navigate = navigate)
            }
        }
}

@Composable
private fun SingleLayout(
    layoutId: String, viewModel: ProductViewModel, navigate: (productId: Long) -> Unit
) {
    val products: List<Product> by viewModel.getRandomListOfProducts(
        layoutId, 1
    ).first.observeAsState(listOf())

    if (products.isNotEmpty()) {
        Spacer(modifier = Modifier.size(16.dp))
        BigItem(products.first(), navigate = navigate)
        Spacer(modifier = Modifier.size(16.dp))
    }
}





