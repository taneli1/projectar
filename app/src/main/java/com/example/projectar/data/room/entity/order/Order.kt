package com.example.projectar.data.room.entity.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * A basic order created by user containing a list
 * of product ids and amounts.
 */
@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val userId: Long,
    val timeStamp: Date,
    val data: Map<Long, Int>,
)