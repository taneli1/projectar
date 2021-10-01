package com.example.projectar.data.datahandlers.product

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.projectar.data.datahandlers.assets.Model
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag

/**
 * Interface combining different functionalities of the application to provide easy
 * access to all the functionalities required in the ViewModels of the application.
 * (or other places requiring product-related data or functions.)
 */
interface ProductManager {
    fun getProducts(pf: TagFilter): LiveData<List<Product>>
    fun getProduct(productId: Long): LiveData<Product>

    fun getProductTags(productId: Long): LiveData<List<Tag>>
    fun getAllTags(): LiveData<List<Tag>>

    fun getProductImage(imageInfo: ImageInfo): Bitmap
    fun getProductModel(modelInfo: ModelInfo): Model

    fun useCart(): Cart
    fun placeOrder()
}
