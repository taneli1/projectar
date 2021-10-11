package com.example.projectar.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.projectar.data.room.queryfilters.SortBy
import com.example.projectar.ui.theme.PADDING_SM
import com.example.projectar.ui.theme.PADDING_XS
import com.example.projectar.ui.theme.Shapes

@Composable
fun OrderingDropdown(
    /**
     * Dropdown menu for item sorting used in MainList screen. 3rd of 3 filtering/sorting fields
     */
    orderingOptions: MutableMap<String, SortBy>,
    sortBy: MutableState<SortBy?>,
    filters: () -> Unit
) {
    val tempList = orderingOptions.keys.toMutableList()
    var expanded by remember { mutableStateOf(false) }
    var selectedItem: String by remember { mutableStateOf(tempList[0]) }

    Surface(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray, shape = Shapes.medium),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(
                    horizontal =
                    PADDING_SM, vertical = PADDING_XS
                )
        ) {
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Test")
            Text(text = selectedItem)
        }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            tempList.forEach { title ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedItem = title
                        sortBy.value = orderingOptions[selectedItem]!!
                        filters()
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