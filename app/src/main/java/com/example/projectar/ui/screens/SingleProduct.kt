package com.example.projectar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.theme.darkGrey
import com.example.projectar.R
import com.example.projectar.ui.components.TopBar

@Composable
fun SingleProduct(product: Product, navController: NavController) {
    Column {
        TopBar(route = "testList", navController = navController)
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
                    .background(darkGrey)
                    .padding(10.dp)
            ) {
                Text(text = product.data.title, color = Color.White)
                Text(text = product.data.price.toString(), color = Color.White)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkGrey)
                    .padding(20.dp)
            ) {
                product.data.description?.let { Text(text = it, color = Color.White) }
            }
        }
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Button(
                modifier = Modifier
                    .padding(10.dp),
                onClick = { /* Do something! */ }, colors = ButtonDefaults.textButtonColors(
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
            Button(
                onClick = { /* Do something! */ }, colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Orange,
                    contentColor = Color.White,
                )
            ) {
                Text(stringResource(R.string.AddToAr))
                Icon(
                    painter = painterResource(id = R.drawable.augmented_reality),
                    contentDescription = R.string.AddToAr.toString()
                )
            }
        }
    }

}