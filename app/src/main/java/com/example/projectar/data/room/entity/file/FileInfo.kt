package com.example.projectar.data.room.entity.file

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.projectar.data.room.entity.product.ProductData

/**
 * Interface providing basic file information required by the application.
 * Used to link files to objects in the db.
 */
interface FileInfo {
    // Primary key
    val id: Long;

    /***  Id of the entity this info belongs to*/
    val refId: Long;
    val filename: String
}