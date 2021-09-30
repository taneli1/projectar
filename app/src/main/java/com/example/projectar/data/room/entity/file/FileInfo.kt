package com.example.projectar.data.room.entity.file

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.projectar.data.room.entity.product.ProductData

/**
 * Interface providing basic file information required by the application.
 * Used to link files to objects in the db.
 *
 * In practise, used in the app to link image + model file names to products.
 * This information can be used in places where a file is required for the UI to get the
 * correct data.
 */
interface FileInfo {
    // Primary key in DB
    val id: Long;

    /***  Id of the entity this info belongs to*/
    val refId: Long;
    val filename: String
}