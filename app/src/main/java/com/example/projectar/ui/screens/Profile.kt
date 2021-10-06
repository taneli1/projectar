package com.example.projectar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectar.ui.components.TopBar
import com.example.projectar.ui.theme.Orange
import com.example.projectar.ui.theme.DarkGrey

@Composable
fun Profile(
    navController: NavController
) {
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
            color = DarkGrey
        )
        Text(
            modifier = Modifier.padding(vertical = 40.dp),
            text = "Order history",
            fontSize = 20.sp
        )
        //Here info and buttons for specific orders
    }
}