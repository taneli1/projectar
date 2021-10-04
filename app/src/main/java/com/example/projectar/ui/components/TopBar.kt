package com.example.projectar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.utils.NavUtils


@Composable
fun TopBar(
    navController: NavController,
    route: String
) {
    TopAppBar(
        title = { Text("Title") },
        backgroundColor = Orange,
        actions = {
            IconButton(onClick = { NavUtils.navigate(navController, route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "Test"
                )
            }
        }
    )
}
