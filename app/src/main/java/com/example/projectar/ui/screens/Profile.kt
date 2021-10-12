package com.example.projectar.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectar.R
import com.example.projectar.data.room.entity.order.Order
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.utils.TagUtils
import com.example.projectar.di.Injector
import com.example.projectar.ui.components.product.ProductCartItem
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.FONT_LG
import com.example.projectar.ui.theme.FONT_MD
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.utils.DateFormatter

@Composable
fun Profile(
    viewModel: ProductViewModel
) {
    val orders: List<Order> by viewModel.getUserOrders(Injector.FAKE_USER_ID)
        .observeAsState(listOf())
    val products: List<Product> by viewModel.getProductsWithTags(TagUtils.getAllTags())
        .observeAsState(listOf())

    LazyColumn() {
        item {
            OrderListHeader(orders = orders)
        }

        itemsIndexed(orders) { index, order ->
            OrderListItem(order = order, products = products)
            if (index != orders.lastIndex) {
                Divider(Modifier.padding(horizontal = 16.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.size(100.dp))
        }
    }
}

/**
 * Header for Order list.
 */
@Composable
private fun OrderListHeader(orders: List<Order>) {
    Column(Modifier.padding(horizontal = 20.dp)) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp, top = 20.dp),
            text = stringResource(id = R.string.profile),
            fontSize = FONT_LG
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Email@email.com",
            color = Orange
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Total orders: ${orders.size}",
            color = DarkGrey
        )
        Text(
            modifier = Modifier.padding(vertical = 40.dp),
            text = "Order history",
            fontSize = 20.sp
        )
    }
}

/**
 * ListItem for an order the user has made.
 * If expanded, shows a list of products which were contained in the order.
 */
@Composable
private fun OrderListItem(order: Order, products: List<Product>) {

    val expanded = remember {
        mutableStateOf(false)
    }

    val iconRes =
        if (expanded.value) R.drawable.ic_baseline_keyboard_arrow_up_24
        else R.drawable.ic_baseline_keyboard_arrow_down_24

    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                expanded.value = !expanded.value
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OrderContent(order = order)

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(id = R.string.content_desc_placeholder),
            tint = Color.Black,
            modifier = Modifier
                .scale(1.2f)
                .padding(20.dp)
        )
    }

    if (expanded.value) {
        OrderProductList(order = order, products = products)
    }
}

/**
 * Content for OrderListItem, shows amount of products ordered + date.
 */
@Composable
private fun OrderContent(order: Order) {
    Column(
        Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24),
                contentDescription = stringResource(id = R.string.content_desc_placeholder),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = stringResource(
                    id = R.string.string_x,
                    order.data.values.reduceOrNull { a, b -> a + b } ?: 0
                ),
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.size(4.dp))

        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = DateFormatter.getDateString(order.timeStamp),
            color = Color.Gray
        )
    }
}

/**
 * List of the products, which were in the order.
 * Shown under OrderListItem, if it is expanded
 */
@Composable
private fun OrderProductList(
    order: Order,
    products: List<Product>
) {

    Spacer(modifier = Modifier.size(20.dp))
    Text(
        text = stringResource(id = R.string.items_in_order),
        fontSize = FONT_MD,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.size(20.dp))
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        for (entry in order.data.entries) {
            val productData = products.find { it.data.id == entry.key }
            productData?.let {
                // Use cart items, provide null functions to hide the buttons
                ProductCartItem(
                    product = productData,
                    count = entry.value,
                    onMinusPressed = null,
                    onPlusPressed = null
                )
            }
            Divider(modifier = Modifier.size(8.dp))
        }
    }
    Spacer(modifier = Modifier.size(20.dp))
}
