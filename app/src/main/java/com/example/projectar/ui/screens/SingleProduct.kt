package com.example.projectar.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.utils.StringUtils

/**
 * Screen for single product's own page. Shows item's price, description,
 * image, name and possibility to add to cart
 */
@Composable
fun SingleProduct(
    product: Product, viewModel: ProductViewModel,
    navController: NavController, trueCart: Cart
) {
    fun checkItemCartStatus(): Int {
        return trueCart.getProductAmount(product.data.id)
    }

    val scroll = rememberScrollState(0)

    val productCount: Int by trueCart.getAll().switchMap {
        val count = it.getOrDefault(product.data.id, 0)
        return@switchMap MutableLiveData(count)
    }.observeAsState(0)

    checkItemCartStatus()
    Column(
        Modifier
            .padding(top = 10.dp, bottom = 110.dp, end = 10.dp, start = 10.dp)
            .shadow(10.dp)
            .clip(Shapes.medium)
    ) {
        product.image?.let { viewModel.getImage(it) }?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = stringResource(id = R.string.content_desc_product),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(10.dp)
        ) {
            Text(
                text = product.data.title,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(0.5f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            Text(
                text = StringUtils.formatFloat(product.data.price) + "€",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(1.0f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Right
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            product.data.description?.let {
                Text(
                    text = it, color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scroll)
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .padding(bottom = 55.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        if (productCount > 0) {
            Row() {
                Button(
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = {
                        trueCart.removeItem(product.data.id)
                        Toast.makeText(
                            navController.context,
                            R.string.removedFromCart,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }, colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Orange,
                        contentColor = Color.White,
                    )
                ) {
                    Text(stringResource(R.string.remove))
                    Icon(
                        painter = painterResource(id = R.drawable.cart_arrow_up),
                        contentDescription = R.string.AddToCart.toString()
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = {
                        trueCart.addItem(product.data.id)
                        Toast.makeText(
                            navController.context,
                            R.string.addedToCart,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }, colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Orange,
                        contentColor = Color.White,
                    )
                ) {
                    Text(
                        stringResource(id = R.string.in_cart, productCount)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.cart_arrow_down),
                        contentDescription = stringResource(id = R.string.cart)
                    )
                }
            }
        } else {
            Button(
                modifier = Modifier
                    .padding(10.dp),
                onClick = {
                    trueCart.addItem(product.data.id)
                    Toast.makeText(navController.context, R.string.addedToCart, Toast.LENGTH_SHORT)
                        .show()
                }, colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Orange,
                    contentColor = Color.White,
                )
            ) {
                Text(stringResource(R.string.AddToCart))
                Icon(
                    painter = painterResource(id = R.drawable.cart_arrow_down),
                    contentDescription = R.string.AddToCart.toString()
                )
            }
        }
    }
}