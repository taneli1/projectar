package com.example.projectar.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.projectar.R
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.components.animated.AnimateHorizontal
import com.example.projectar.ui.components.common.HeaderWithPadding
import com.example.projectar.ui.components.common.IconButton
import com.example.projectar.ui.functional.ar.ArViewManager
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.ELEVATION_MD
import com.example.projectar.ui.theme.MARGIN_MD


/**
 * UI for the AR View
 */
@ExperimentalAnimationApi
@Composable
fun ArInterface(
    viewModel: ProductViewModel,
    arViewManager: ArViewManager
) {
    // State of Expanding shelf
    val expanded = remember {
        mutableStateOf(false)
    }

    val marginLeft: Dp by animateDpAsState(
        if (expanded.value) 0.dp else MARGIN_MD
    )

    val products: List<Product> by viewModel.useCart().getAll().switchMap {
        val list = mutableListOf<Product>()
        it.forEach { entry ->
            val p = viewModel.products.value?.find { product -> product.data.id == entry.key }
            p?.let { pNotNull ->
                list.add(pNotNull)
            }
        }
        return@switchMap MutableLiveData(list)
    }.observeAsState(listOf())

    Row(
        modifier = Modifier
            .padding(horizontal = marginLeft, vertical = MARGIN_MD)
            .fillMaxHeight(),
    ) {

        AnimateHorizontal(
            visible = expanded,
            content = {
                Surface(
                    color = DarkGrey,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                        .fillMaxHeight(fraction = 0.9f),
                    elevation = ELEVATION_MD,
                    shape = RoundedCornerShape(topEnd = 30f, bottomEnd = 30f),
                ) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                    ) {
                        item {
                            HeaderWithPadding(text = stringResource(id = R.string.header_items))
                        }

                        if (products.isEmpty()) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.ar_empty_cart),
                                    color = Color.White,
                                    modifier = Modifier.padding(
                                        vertical = 20.dp,
                                        horizontal = 40.dp
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            // List of items in the shelf
                            items(products) { product ->
                                ProductArComponent(product) { id ->
                                    arViewManager.setModelSelected(id)
                                    expanded.value = !expanded.value
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.size(40.dp))
                            }
                        }
                    }
                }
            }
        )

        // Button to open the extended UI
        IconButton(
            onClick = { expanded.value = !expanded.value },
            drawableRes = if (expanded.value) R.drawable.ic_baseline_arrow_back_32 else R.drawable.ic_baseline_arrow_forward_24
        )
    }
}


@Composable
fun ProductArComponent(
    product: Product,
    onItemClick: (productId: Long) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth(0.95f)
            .offset(y = 20.dp)
            .zIndex(1f), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            drawableRes = R.drawable.ic_baseline_delete_outline_24,
        )
        IconButton(
            onClick = { /*TODO*/ },
            drawableRes = R.drawable.ic_baseline_shopping_cart_24,
        )
    }

    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .zIndex(0f)
            .clickable {
                onItemClick(product.data.id)
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.goat),
            contentDescription = stringResource(id = R.string.content_desc_placeholder)
        )
    }

}

