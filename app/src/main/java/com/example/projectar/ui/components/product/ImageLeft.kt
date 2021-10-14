package com.example.projectar.ui.components.product

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectar.R
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.components.common.IconButton
import com.example.projectar.ui.theme.ELEVATION_SM
import com.example.projectar.ui.theme.FONT_XS
import com.example.projectar.ui.theme.PADDING_SM
import com.example.projectar.ui.theme.PADDING_XS
import com.example.projectar.ui.utils.StringUtils

/**
 * Returns a layout to show products, with +/- buttons.
 * If functions were not provided for the buttons, they do not render.
 */
@Composable
fun ProductCartItem(
    image: Bitmap?,
    product: Product,
    count: Int?,
    onMinusPressed: ((productId: Long) -> Unit)?,
    onPlusPressed: ((productId: Long) -> Unit)?
) {
    val img = image ?: Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8)

    Row(
        Modifier
            .padding(horizontal = 20.dp)
    ) {
        Surface(
            elevation = ELEVATION_SM, modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        ) {
            Image(
                modifier = Modifier
                    .height(85.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
                bitmap = img.asImageBitmap(),
                contentDescription = stringResource(id = R.string.content_desc_product)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .padding(horizontal = PADDING_SM, vertical = PADDING_XS)
            ) {
                Text(
                    text = product.data.title,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(0.65f)
                )
                Text(text = "${StringUtils.formatFloat(product.data.price)}€", fontSize = FONT_XS)
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(vertical = PADDING_XS)
            ) {
                Text(
                    text = "x$count",
                    Modifier
                        .align(Alignment.End)
                        .padding(bottom = 18.dp),
                    fontSize = 18.sp
                )
                // Hide buttons if functions for them were not provided
                if (onMinusPressed != null && onPlusPressed != null) {
                    Row() {
                        IconButton(
                            onClick = { onMinusPressed(product.data.id) },
                            drawableRes = R.drawable.ic_baseline_remove_24,
                            shape = RoundedCornerShape(6.dp),
                            buttonSize = 36.dp
                        )
                        IconButton(
                            onClick = { onPlusPressed(product.data.id) },
                            drawableRes = R.drawable.ic_baseline_add_24,
                            shape = RoundedCornerShape(6.dp),
                            buttonSize = 36.dp
                        )
                    }
                }

            }
        }
    }
}
