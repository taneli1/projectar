package com.example.projectar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectar.ui.components.BottomBar
import com.example.projectar.ui.components.ItemBox
import com.example.projectar.ui.components.TopBar
import com.example.projectar.ui.components.TopBarWithBurger
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.darkGrey

@Composable
fun Profile(
    navController: NavController
) {
    Scaffold(
        topBar = { TopBar(navController, route = "testList") },
        bottomBar = { BottomBar() },
        content = {
            Column(Modifier.padding(20.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = "My account",
                    fontSize = 30.sp
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "Email@email.com",
                    color = Orange
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "Total orders: 1",
                    color = darkGrey
                )
                Text(
                    modifier = Modifier.padding(vertical = 40.dp),
                    text = "Order history",
                    fontSize = 20.sp
                )

                //Here info and buttons for specific orders
            }
        })
}