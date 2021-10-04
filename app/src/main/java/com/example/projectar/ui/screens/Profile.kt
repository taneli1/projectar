package com.example.projectar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.projectar.ui.components.BottomBar
import com.example.projectar.ui.components.ItemBox
import com.example.projectar.ui.components.TopBar
import com.example.projectar.ui.components.TopBarWithBurger

@Composable
fun Profile(
        navController: NavController
) {
    Scaffold(topBar = { TopBar(navController, route = "testList") }, bottomBar = { BottomBar() }, content = {
        Text(text = "Hello there body")
    })
}