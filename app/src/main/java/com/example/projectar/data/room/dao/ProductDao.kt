package com.example.projectar.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.product.ProductData

@Dao
abstract class ProductDao {
    /**
     * Returns all products
     */
    @Transaction
    @Query("SELECT * FROM productdata")
    abstract fun getAllProducts(): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM productdata WHERE id = :id")
    abstract fun getProduct(id: Long): LiveData<Product>

    @Insert
    fun insertProduct(product: Product): Long {
        val id = insertData(product.data)

        // Replace image + model refs
        val image = product.image.let {
            ImageInfo(
                it.id,
                id,
                it.filename,
                it.thumbnail
            )
        }

        val model = product.model.let {
            ModelInfo(
                it.id,
                id,
                it.filename
            )
        }

        insertImageInfo(image)
        insertModeInfo(model)

        return id
    }

    @Insert
    abstract fun insertData(data: ProductData): Long

    @Insert
    abstract fun insertImageInfo(info: ImageInfo): Long

    @Insert
    abstract fun insertModeInfo(info: ModelInfo): Long
}