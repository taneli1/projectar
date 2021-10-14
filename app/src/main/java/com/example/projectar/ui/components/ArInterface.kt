package com.example.projectar.ui.components

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.projectar.ui.theme.*


/**
 * UI for the AR View
 */
@ExperimentalAnimationApi
@Composable
fun ArInterface(
    viewModel: ProductViewModel,
    arViewManager: ArViewManager<Long>
) {
    // Initial guide shown
    val guideShown = remember {
        mutableStateOf(false)
    }

    // State of Expanding shelf which lists the products
    val expanded = remember {
        mutableStateOf(false)
    }

    val stateMargin: Dp by animateDpAsState(
        if (expanded.value) 0.dp else MARGIN_MD
    )

    // Get the product data for the selected productIds, since cart does not contain the product data itself.
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

    // Product title of the user selected product, which is to be added to the scene
    val selectedModelProductName: String? by arViewManager.modelSelected.switchMap {
        val p = viewModel.products.value?.find { product -> product.data.id == it }
        // When the user has selected a product, guide no longer needs to be shown
        if (p != null) {
            guideShown.value = true
        }
        return@switchMap MutableLiveData(p?.data?.title)
    }.observeAsState()

    // Counts of rendered product models
    val modelCounts: Map<Long, Int> by arViewManager.renderedModels.observeAsState(mapOf())

    Row(
        modifier = Modifier
            .padding(horizontal = stateMargin, vertical = MARGIN_MD)
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
                                ListEmptyComponent()
                            }
                        } else {
                            // Guide text
                            item {
                                Text(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = Color.Gray,
                                    text = stringResource(id = R.string.ar_view_guide)
                                )
                            }
                            // List of the items which can be added to the scene
                            items(products) { product ->
                                ProductArComponent(
                                    viewModel,
                                    product,
                                    amountRendered = modelCounts.getOrDefault(product.data.id, 0),
                                    removeRenderedModel = arViewManager::removeModel,
                                    onItemClick = { id ->
                                        arViewManager.setModelSelected(id)
                                        expanded.value = !expanded.value
                                    }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.size(40.dp))
                            }
                        }
                    }
                }

            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Button to open the extended UI
            IconButton(
                onClick = { expanded.value = !expanded.value },
                drawableRes = if (expanded.value) R.drawable.ic_baseline_arrow_back_32 else R.drawable.ic_baseline_arrow_forward_24
            )

            // Initial guide
            if (!guideShown.value && !expanded.value) {
                Surface(
                    color = DarkGrey,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(Shapes.medium)
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.ar_add_guide),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            // Guide text when model is selected
            if (selectedModelProductName != null && !expanded.value) {
                Surface(
                    color = DarkGrey,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(Shapes.medium)
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(
                            id = R.string.ar_click_guide,
                            selectedModelProductName.toString()
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ProductArComponent(
    viewModel: ProductViewModel,
    product: Product,
    onItemClick: (productId: Long) -> Unit,
    amountRendered: Int,
    removeRenderedModel: (productId: Long) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth(0.95f)
            .offset(y = 20.dp)
            .zIndex(1f), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (amountRendered != 0) {
            IconButton(
                onClick = { removeRenderedModel(product.data.id) },
                drawableRes = R.drawable.ic_baseline_clear_24,
            )

            Surface(color = Orange, shape = CircleShape, modifier = Modifier.size(48.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = amountRendered.toString(), color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .zIndex(0f)
            .clickable {
                onItemClick(product.data.id)
            }
    ) {
        product.image?.let { viewModel.getImage(it).asImageBitmap() }?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                bitmap = it,
                contentDescription = stringResource(id = R.string.content_desc_product)
            )
        }
    }

}


@Composable
private fun ListEmptyComponent() {
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