package com.example.projectar.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.projectar.R


@Composable
fun BottomBar() {
    val selectedItem = remember { mutableStateOf("upload")}
    val result = remember { mutableStateOf("") }

    BottomAppBar(
        content = {
            BottomNavigation() {
                BottomNavigationItem(
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_person_24), contentDescription = "Test")
                    },
                    label = { Text(text = "Save") },
                    selected = selectedItem.value == "save",
                    onClick = {
                        result.value = "Upload icon clicked"
                        selectedItem.value = "save"
                    },
                    alwaysShowLabel = false
                )

                BottomNavigationItem(
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_person_24), contentDescription = "Test")
                    },


                    label = { Text(text = "Upload") },
                    selected = selectedItem.value == "upload",
                    onClick = {
                        result.value = "Upload icon clicked"
                        selectedItem.value = "upload"
                    },
                    alwaysShowLabel = false
                )

                BottomNavigationItem(
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.augmented_reality), contentDescription = "Test")
                    },
                    label = { Text(text = "Download") },
                    selected = selectedItem.value == "download",
                    onClick = {
                        result.value = "Download icon clicked"
                        selectedItem.value = "download"
                    },
                    alwaysShowLabel = false
                )
            }
        }
    )

}