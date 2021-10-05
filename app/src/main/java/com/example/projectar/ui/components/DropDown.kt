package com.example.projectar.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projectar.data.appdata.tags.ProductTags
import com.example.projectar.data.utils.TagUtils


@Composable
fun Dropdown(items: List<ProductTags>, selectedItems: MutableList<ProductTags>) {
    var expanded by remember { mutableStateOf(false) }

    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            items[selectedIndex].toString(), modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
                .background(
                    Color.Gray
                )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, productTags ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    val isChecked = remember { mutableStateOf(true) }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(text = productTags.toString())
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it },
                        )
                    }
                    if (!isChecked.value) {
                        selectedItems.remove(productTags)
                        Log.d("APPLIED TAGS: ", selectedItems.toString())
                    }
                }
            }
        }
    }
}