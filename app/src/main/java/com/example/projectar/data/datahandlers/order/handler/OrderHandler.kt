package com.example.projectar.data.datahandlers.order.handler

import com.example.projectar.data.room.entity.order.Order

/**
 * Classes that can handle orders (Talk to server / db).
 */
interface OrderHandler {
    /**
     * Forwards an order to service
     */
    fun placeOrder(order: Order)
}