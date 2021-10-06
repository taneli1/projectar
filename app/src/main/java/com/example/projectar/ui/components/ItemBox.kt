package com.example.projectar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projectar.R
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.theme.DarkGrey

@Composable
fun ItemBox(product: Product, navigate: (productId: Long) -> Unit) {
    Column(
        Modifier
            .padding(10.dp)
            .clip(Shapes.medium)
            .selectable(selected = true, onClick = { navigate(product.data.id) })
    ) {
        Image(
            painter = painterResource(R.drawable.blenny),
            contentDescription = "picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(5.dp)
        ) {
            Text(text = product.data.title, color = Color.White)
            Text(text = product.data.price.toString(), color = Color.White)
        }
    }
}