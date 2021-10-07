package com.example.projectar.data.datahandlers.order.handler

import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.entity.order.Order

/**
 * OrderHandler forwarding the orders to local Room DB.
 */
class LocalOrderHandler(private val repo: ProductRepository) : OrderHandler {

    override fun placeOrder(order: Order) {
        repo.insertOrder(order)
    }
}