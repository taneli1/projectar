package com.example.projectar.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.components.product.ProductCartItem
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.FONT_LG
import com.example.projectar.ui.theme.FONT_MD
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.Success
import com.example.projectar.ui.utils.NotificationBuilder
import com.example.projectar.ui.utils.StringUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val CHANNEL_ID = "1234"

@ExperimentalAnimationApi
@Composable
fun Cart(viewModel: ProductViewModel, navController: NavController) {

    // Has the user placed an order
    val orderPlaced = remember {
        mutableStateOf(false)
    }

    // Clear cart when navigating out
    DisposableEffect(navController) {
        val onNavigateOutListener = NavController.OnDestinationChangedListener { _, _, _ ->
            viewModel.useCart().removeUnselected()
        }

        // Attach listener when component is rendered
        navController.addOnDestinationChangedListener(onNavigateOutListener)

        // When component is disposed, remove the listener
        onDispose {
            navController.removeOnDestinationChangedListener(onNavigateOutListener)
        }
    }

    val totalProductCount = viewModel.useCart().getCartTotal().observeAsState(0)

    // Wrap Product data with the amount of it selected by the user into observable state
    val productsWithCounts: Map<Product, Int> by viewModel.useCart().getAll().switchMap {
        val products = mutableMapOf<Product, Int>()
        it.forEach { entry ->
            val product = viewModel.products.value?.find { it.data.id == entry.key }
            product?.let { prod ->
                products[prod] = viewModel.useCart().getProductAmount(prod.data.id)
            }
        }
        return@switchMap MutableLiveData(products)
    }.observeAsState(mapOf())

    // Calc the total price of the items
    val totalPrice: Float by viewModel.useCart().getAll().switchMap { map ->
        var price = 0f
        map.entries.forEach { entry ->
            val product = viewModel.products.value?.find { it.data.id == entry.key }
            price += (entry.value * (product?.data?.price ?: 0f))
        }
        return@switchMap MutableLiveData(price)
    }.observeAsState(0f)

    fun onPlusPress(id: Long) {
        viewModel.useCart().addItem(id)
    }

    fun onMinusPress(id: Long) {
        viewModel.useCart().removeItem(id)
    }

    fun onOrderPressed() {
        viewModel.placeOrder()
        orderPlaced.value = true
    }

    if (orderPlaced.value) {
        OrderSuccess()
    } else {
        LazyColumn() {
            item {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = stringResource(id = R.string.cart),
                        fontSize = FONT_LG
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(start = 2.dp),
                        text = stringResource(
                            id = R.string.cart_items,
                            totalProductCount.value.toString()
                        ),
                        color = Orange,
                        fontSize = FONT_MD
                    )
                }
            }

            itemsIndexed(productsWithCounts.entries.toList()) { index, entry ->
                ProductCartItem(
                    entry.key.image?.let { viewModel.getImage(it) },
                    entry.key,
                    entry.value,
                    onMinusPressed = ::onMinusPress,
                    onPlusPressed = ::onPlusPress
                )

                // Don't show divider on last item
                if (index != productsWithCounts.entries.toList().lastIndex) {
                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 18.dp)
                    )
                }
            }

            item {
                CartFooter(totalPrice, ::onOrderPressed)
            }
        }
    }
}


@Composable
fun OrderSuccess() {
    val bool = remember {
        mutableStateOf(true)
    }

    // "Fade in Animation" for components
    val iconColor by animateColorAsState(
        if (bool.value) Color.White else Success,
        animationSpec = tween(durationMillis = 1500)
    )
    val textColor by animateColorAsState(
        if (bool.value) Color.White else Color.Black,
        animationSpec = tween(durationMillis = 1500)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        if (bool.value) {
            CircularProgressIndicator(
                color = Orange
            )
            NotificationBuilder.sendTestNotification(context = LocalContext.current)
        }
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_check_circle_outline_64),
                contentDescription = stringResource(id = R.string.success_image),
                tint = iconColor
            )
            Text(text = stringResource(id = R.string.order_success), color = textColor)
        }
    }

    val scope = rememberCoroutineScope()

    // Fake delay hide loading indicator after delay, show successful
    LaunchedEffect(true) {
        scope.launch {
            delay(1500)
            bool.value = false
        }
    }
}

@Composable
private fun CartFooter(totalPrice: Float, onOrderPressed: () -> Unit) {
    val cartEmpty = totalPrice == 0f

    Spacer(modifier = Modifier.height(40.dp))
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Cart total ${StringUtils.formatFloat(totalPrice)}€",
            textAlign = TextAlign.Center,
            fontSize = FONT_MD
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { onOrderPressed() },
            enabled = !cartEmpty,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = if (cartEmpty) Color.Gray.copy(alpha = 0.4f) else Orange,
                contentColor = Color.White,
                disabledContentColor = Color.Black.copy(alpha = 0.4f)
            )
        ) {
            Text(text = stringResource(id = R.string.place_order))
        }
    }
    Spacer(modifier = Modifier.height(100.dp))
}