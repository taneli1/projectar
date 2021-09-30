package com.example.projectar.data.datahandlers.order.builder

import com.example.projectar.data.room.entity.order.Order
import java.util.*

/**
 * Builds orders.
 * @param userId Currently logged in user
 */
class OrderBuilderImpl(
    private val userId: Long
) : OrderBuilder {
    override fun buildOrder(selections: Map<Long, Int>): Order {
        return Order(
            0,
            userId,
            Date(),
            selections
        )
    }
}