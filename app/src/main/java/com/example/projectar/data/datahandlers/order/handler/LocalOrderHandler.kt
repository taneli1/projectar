package com.example.projectar.data.datahandlers.order.handler

import com.example.projectar.data.repository.ProductRepositoryImpl
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.entity.order.Order
import java.util.concurrent.Future

/**
 * OrderHandler forwarding the orders to local Room DB.
 */
class LocalOrderHandler(private val repo: ProductRepository) : OrderHandler {

    override suspend fun placeOrder(order: Order): Boolean {
        repo.insertOrder(order)
        return true
    }
}