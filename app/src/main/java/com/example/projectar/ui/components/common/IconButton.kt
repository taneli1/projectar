package com.example.projectar.ui.components.common

import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projectar.R
import com.example.projectar.ui.theme.ELEVATION_SM
import com.example.projectar.ui.theme.Orange


/**
 * IconButton with the application theme colors.
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    drawableRes: Int,
    scale: Float = 1f
) {
    Surface(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(CircleShape), elevation = ELEVATION_SM, color = Orange
    ) {
        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                painter = painterResource(id = drawableRes),
                contentDescription = stringResource(id = R.string.icon_arrow_forward),
                tint = Color.White,
                modifier = Modifier.scale(scale)
            )
        }
    }
}
