package com.example.projectar.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.utils.StringUtils

@Composable
fun SingleProduct(product: Product, navController: NavController, trueCart: Cart) {
    fun checkItemCartStatus(): Int {
        val isInCart = trueCart.getProductAmount(product.data.id)
        Log.d("Amount", "amount of products: $isInCart")
        return isInCart
    }

    val productCount: Int by trueCart.getAll().switchMap {
        val count = it.getOrDefault(product.data.id, 0)
        return@switchMap MutableLiveData(count)
    }.observeAsState(0)

    checkItemCartStatus()
    Column(
        Modifier
            .padding(10.dp)
            .shadow(10.dp)
            .clip(Shapes.medium)
    ) {
        Image(
            painter = painterResource(R.drawable.blenny),
            contentDescription = "picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(10.dp)
        ) {
            Text(text = product.data.title, color = Color.White)
            Text(text = StringUtils.formatFloat(product.data.price) + "€", color = Color.White)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp)
        ) {
            product.data.description?.let { Text(text = it, color = Color.White) }
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
            Button(
                modifier = Modifier
                    .padding(10.dp),
                onClick = {
                    trueCart.removeItem(product.data.id)
                    Toast.makeText(navController.context, "Removed from cart", Toast.LENGTH_SHORT)
                        .show()
                }, colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Orange,
                    contentColor = Color.White,
                )
            ) {
                Text("Remove from cart")
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
                    Toast.makeText(navController.context, R.string.addedToCart, Toast.LENGTH_SHORT)
                        .show()
                }, colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Orange,
                    contentColor = Color.White,
                )
            ) {
                Text("In cart: $productCount, click to add more")
                Icon(
                    painter = painterResource(id = R.drawable.cart_arrow_down),
                    contentDescription = "In cart: $productCount, click to add more"

                )
            }
        }
        else {
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