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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.projectar.R
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.Shapes
import com.example.projectar.ui.utils.StringUtils

/**
 * The basic box containing 1 item and it's price and name on dark background.
 * Scalable horizontally.
 * Used in multiple views like Home and MainList
 */
@Composable
fun ItemBox(
    product: Product,
    width: Float = 1.0f,
    navigate: (productId: Long) -> Unit,
    viewModel: ProductViewModel
) {

    Column(
        Modifier
            .fillMaxWidth(width)
            .padding(8.dp)
            .clip(Shapes.medium)
            .selectable(selected = true, onClick = { navigate(product.data.id) }),
    ) {
        product.image?.let { viewModel.getImage(it) }?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = stringResource(id = R.string.content_desc_product),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(8.dp)
        ) {
            Text(
                text = product.data.title,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Text(
                text = StringUtils.formatFloat(product.data.price) + "€",
                color = Color.White,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(1.0f),
                textAlign = TextAlign.Right
            )
        }

    }
}
