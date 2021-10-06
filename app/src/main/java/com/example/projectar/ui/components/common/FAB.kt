package com.example.projectar.ui.components.common

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.projectar.R
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
            contentColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24),
                stringResource(
                    id = R.string.content_desc_placeholder
                ),
                Modifier.scale(1.1f)
            )
        }
    }
}