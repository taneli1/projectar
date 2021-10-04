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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavAction
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.navigate
import com.example.projectar.ui.theme.Orange

@Composable
fun TopBarWithBurger(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text("Title") },
        backgroundColor = Orange,
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_menu_24), contentDescription = "test")
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = { navController.navigate("profile") }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_person_24), contentDescription = "test")
                    Text(text = stringResource(R.string.profile))
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24), contentDescription = "test")
                    Text(text = stringResource(R.string.cart))
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_menu_24), contentDescription = "test")
                    Text(text = stringResource(R.string.rooms))
                }
            }
        }
    )
}