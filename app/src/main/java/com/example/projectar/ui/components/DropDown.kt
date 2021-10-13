package com.example.projectar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projectar.R
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.ui.theme.ELEVATION_SM
import com.example.projectar.ui.theme.PADDING_SM
import com.example.projectar.ui.theme.PADDING_XS
import com.example.projectar.ui.theme.Shapes


@Composable
fun Dropdown(
    items: List<ProductTag>,
    selectedItems: MutableList<ProductTag>,
    filters: () -> Unit
) {
    /**
     * Dropdown for filtering items used in MainList screen. 2nd of 3 filtering/sorting fields
     */
    var expanded by remember { mutableStateOf(false) }

    fun filterCheckChange(isChecked: MutableState<Boolean>, productTag: ProductTag) {
        isChecked.value = !isChecked.value
        if (!isChecked.value) {
            selectedItems.remove(productTag)
        } else selectedItems.add(productTag)
        filters()
    }

    Surface(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .fillMaxWidth(0.5f)
            .border(width = 1.dp, color = Color.Gray, shape = Shapes.medium)
            .clickable { expanded = !expanded },
        elevation = ELEVATION_SM
    ) {
        Row(
            Modifier
                .background(Color.White)
                .padding(horizontal = PADDING_SM, vertical = PADDING_XS)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_format_list_bulleted_24),
                contentDescription = stringResource(id = R.string.content_desc_placeholder),
                tint = Color.Gray
            )
            Divider(Modifier.width(PADDING_SM))
            Text(stringResource(id = R.string.categories))
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach() { productTag ->
                val isChecked = remember {
                    mutableStateOf(selectedItems.contains(productTag))
                }
                DropdownMenuItem(onClick = {
                    filterCheckChange(isChecked, productTag)
                }) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(text = productTag.toString())
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = {
                                filterCheckChange(isChecked, productTag)
                            },
                        )
                    }
                }
            }
        }
    }
}