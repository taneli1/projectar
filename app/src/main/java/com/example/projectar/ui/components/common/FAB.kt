package com.example.projectar.ui.components.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.projectar.R
import com.example.projectar.ui.theme.FONT_SM
import com.example.projectar.ui.theme.Orange

@Composable
fun CartFAB(
    onClick: () -> Unit,
    visible: Boolean,
    itemCount: Int = 0,
) {
    if (visible) {
        FloatingActionButton(
            onClick = onClick,
            backgroundColor = Orange,
            contentColor = Color.White,
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24),
                stringResource(
                    id = R.string.content_desc_placeholder
                ),
                Modifier.scale(1.1f)
            )
        }

        if (itemCount != 0) {
            Surface(
                color = Orange,
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = 36.dp, y = (-8).dp)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape),
                shape = CircleShape,
            ) {
                Text(
                    text = itemCount.toString(),
                    color = Color.White,
                    fontSize = FONT_SM,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

//if (visible) {
//    FloatingActionButton(
//        onClick = onClick,
//        backgroundColor = Orange,
//        contentColor = Color.White,
//    ) {
//
//        Text(
//            text = itemCount.toString(),
//            color = Color.Black,
//            fontSize = FONT_MD,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.offset(x = 15.dp, y = (-20).dp),
//            overflow = TextOverflow.Visible
//        )
//
//        Icon(
//            painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24),
//            stringResource(
//                id = R.string.content_desc_placeholder
//            ),
//            Modifier.scale(1.1f)
//        )
//
//    }
//}