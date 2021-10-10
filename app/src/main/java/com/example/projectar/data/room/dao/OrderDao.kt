package com.example.projectar.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.projectar.data.room.entity.order.Order

@Dao
interface OrderDao : BaseDao<Order> {
    @Query("SELECT * FROM `order` WHERE userId = :userId ORDER BY timeStamp DESC")
    fun getOrders(userId: Long): LiveData<List<Order>>
}