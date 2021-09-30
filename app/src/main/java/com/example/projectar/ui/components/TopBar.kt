package com.example.projectar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.navigate
import com.example.projectar.ui.theme.Orange

@Composable
fun TopBar(
    navController: NavController,
    route: String
    //content: RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = Orange)
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = R.string.AddToAr.toString(),
            Modifier.clickable {
                navigate(navController, route)
            }
        )
        Text(text = "hello")
    }
}