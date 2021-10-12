package com.example.projectar.ui.components.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.projectar.R
import com.example.projectar.ui.theme.ELEVATION_LG
import com.example.projectar.ui.theme.Orange


/**
 * IconButton with the application theme colors.
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    drawableRes: Int,
    buttonSize: Dp = 48.dp,
    shape: Shape = CircleShape
) {
    // Scale icons down if size is changed
    val iconPercentage = buttonSize / 48.dp

    Surface(
        modifier = Modifier
            .width(buttonSize)
            .height(buttonSize)
            .padding(2.dp)
            .clip(shape),
        elevation = ELEVATION_LG,
        color = Orange
    ) {
        IconButton(
            onClick = { onClick() },
        ) {
            Icon(
                painter = painterResource(id = drawableRes),
                contentDescription = stringResource(id = R.string.icon_arrow_forward),
                tint = Color.White,
                modifier = Modifier.scale(iconPercentage)
            )
        }
    }
}
