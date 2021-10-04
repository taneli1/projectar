package com.example.projectar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import com.example.projectar.ui.theme.Orange

@Composable
fun TopBarWithBurger() {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text("Title") },
        backgroundColor = Orange,
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "test")
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "test")
                    Text(text = "Choice 1")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "test")
                    Text(text = "Choice 2")
                }
            }
        }
    )
}