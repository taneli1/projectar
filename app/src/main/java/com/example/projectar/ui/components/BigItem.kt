package com.example.projectar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.Background
import com.example.projectar.ui.utils.ColorUtils
import com.example.projectar.ui.utils.StringUtils

@Composable
fun BigItem(product: Product, navigate: (productId: Long) -> Unit, viewModel: ProductViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = true, onClick = { navigate(product.data.id) })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    ColorUtils.getBackGroundColor(
                        product.data.price
                    )
                )
                .padding(start = 50.dp, bottom = 25.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp, top = 25.dp, end = 10.dp)
            ) {
                Text(
                    text = product.data.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Text(
                    text = StringUtils.formatFloat(product.data.price) + "€",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth(1.0f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Right
                )
            }
            product.image?.let { viewModel.getImage(it) }?.asImageBitmap()?.let {
                Image(
                    bitmap = it,
                    contentDescription = "picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp)

                )
            }
            Text(
                text = product.data.description.toString(),
                color = Color.White,
                modifier = Modifier
                    .background(color = Background)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
