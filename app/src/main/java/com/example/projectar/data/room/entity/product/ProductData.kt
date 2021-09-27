package com.example.projectar.data.room.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ProductData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val price: Float,
    val description: String?,
)
