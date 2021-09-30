package com.example.projectar.data.datahandlers.order.builder

import com.example.projectar.data.room.entity.order.Order

/**
 * Build orders from selections provided to it.
 */
interface OrderBuilder {
    fun buildOrder(selections: Map<Long, Int>): Order
}