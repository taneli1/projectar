package com.example.projectar.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.projectar.R
import com.example.projectar.ui.theme.Orange

@Composable
fun TopBarWithBurger(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text("Title") },
        backgroundColor = Orange,
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                    contentDescription = "test"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = { navController.navigate("profile") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_person_24),
                        contentDescription = "test"
                    )
                    Text(text = stringResource(R.string.profile))
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24),
                        contentDescription = "test"
                    )
                    Text(text = stringResource(R.string.cart))
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                        contentDescription = "test"
                    )
                    Text(text = stringResource(R.string.rooms))
                }
            }
        }
    )
}