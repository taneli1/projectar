package com.example.projectar.data.room.entity.tag

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.projectar.data.room.entity.product.ProductData

/**
 * Represents a tag which another entity can have.
 */
@Entity
data class Tag(
    @PrimaryKey val tagId: Long,
)
