package com.example.projectar.data.room.intrfc

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.projectar.data.room.entity.product.ProductData

/**
 * Interface providing file information.
 */

interface FileInfo {
    // Primary key
    val id: Long;

    /**
     *  Id of the entity this info belongs to
     */
    val refId: Long;
    val filename: String
}