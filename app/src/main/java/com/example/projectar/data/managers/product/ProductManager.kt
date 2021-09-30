package com.example.projectar.data.managers.product

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.projectar.data.managers.assets.Model
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag

/**
 * Interface combining different functionalities of the application, to provide easy
 * access to all the data required in the ViewModels of the application.
 * (or other places requiring this data.)
 */
interface ProductManager {
    fun getProducts(pf: TagFilter): LiveData<List<Product>>
    fun getProduct(productId: Long): LiveData<Product>

    fun getProductTags(productId: Long): LiveData<List<Tag>>
    fun getAllTags(): LiveData<List<Tag>>

    fun getProductImage(imageInfo: ImageInfo): Bitmap
    fun getProductModel(modelInfo: ModelInfo): Model
}
