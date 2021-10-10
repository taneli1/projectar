package com.example.projectar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectar.R
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.theme.*
import com.example.projectar.ui.utils.StringUtils

@Composable
fun BigItem(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Beige)
            .padding(start = 50.dp, bottom = 25.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 25.dp, top = 25.dp, end = 10.dp)
        ) {
            Text(text = product.data.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Text(text = StringUtils.formatFloat(product.data.price) + "€", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        }
        Image(
            painter = painterResource(R.drawable.blenny),
            contentDescription = "picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp)
        )
        Text(text = product.data.description.toString(), color = Color.White, modifier = Modifier.background(color = Background).fillMaxWidth().padding(5.dp))
    }
}