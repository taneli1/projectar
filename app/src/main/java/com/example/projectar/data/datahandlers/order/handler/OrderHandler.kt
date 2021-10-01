package com.example.projectar.data.datahandlers.order.handler

import com.example.projectar.data.room.entity.order.Order
import java.util.concurrent.Future

/**
 * Classes that can handle orders (Talk to server / db).
 */
interface OrderHandler {
    /**
     * Forwards an order to service
     * @return true if successfully placed order
     */
    suspend fun placeOrder(order: Order): Boolean
}