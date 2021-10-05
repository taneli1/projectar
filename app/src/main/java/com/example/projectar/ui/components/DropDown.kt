package com.example.projectar.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projectar.data.appdata.tags.ProductTags
import com.example.projectar.data.utils.TagUtils
import com.example.projectar.ui.functional.viewmodel.ProductViewModel


@Composable
fun Dropdown(
    items: List<ProductTags>,
    selectedItems: MutableList<ProductTags>,
    viewModel: ProductViewModel,
    filters: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    fun filterCheckChange(isChecked: MutableState<Boolean>, it: Boolean, productTag: ProductTags) {
        isChecked.value = it
        if (!isChecked.value) {
            selectedItems.remove(productTag)
        } else selectedItems.add(productTag)
        filters()
    }

    Surface(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .fillMaxWidth(0.5f),
        ) {
        Text(
            items[selectedIndex].toString(), modifier = Modifier
                .clickable(onClick = { expanded = true })
                .background(
                    Color.Gray
                )
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, productTag ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    val isChecked = remember {
                        mutableStateOf(selectedItems.contains(productTag))
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(text = productTag.toString())
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { filterCheckChange(isChecked, it, productTag) },
                        )
                    }
                }
            }
        }
    }
}