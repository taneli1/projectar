package com.example.projectar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectar.R
import com.example.projectar.data.room.entity.order.Order
import com.example.projectar.di.Injector
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.FONT_LG
import com.example.projectar.ui.theme.Orange

@Composable
fun Profile(
    viewModel: ProductViewModel
) {
    val orders: List<Order> by viewModel.getUserOrders(Injector.FAKE_USER_ID)
        .observeAsState(listOf())

    Column(Modifier.padding(20.dp)) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = stringResource(id = R.string.profile),
            fontSize = FONT_LG
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Email@email.com",
            color = Orange
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Total orders: ${orders.size}",
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