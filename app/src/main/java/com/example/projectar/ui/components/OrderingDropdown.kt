package com.example.projectar.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.projectar.ui.theme.Orange

@Composable
fun OrderingDropdown(orderingOptions: List<String>, ordering: (ordering: String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem: String by remember { mutableStateOf(orderingOptions[0]) }

    Surface(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .fillMaxWidth(),
        color = Orange
    ) {
        Row(modifier = Modifier
            .clickable
            {
                expanded = !expanded
            }) {
            Text(text = selectedItem)
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Test")
        }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            orderingOptions.forEach { title ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedItem = title
                        ordering(selectedItem)
                    }) {
                    val isSelected = title == selectedItem
                    val style = if (isSelected) {
                        MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.secondary
                        )
                    } else {
                        MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                    Text(text = title, style = style)
                }
            }
        }
    }
}