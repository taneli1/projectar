package com.example.projectar.ui.functional.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.order.Order
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.queryfilters.TagFilter

interface ProductViewModel {
    /** List of products matching the current provided filter */
    val products: LiveData<List<Product>>

    /** Set a filter for searched products */
    fun applyFilter(filter: TagFilter)


    // --- Product data ---
    // --- Product data ---


    /** Get all data for a product except its tags */
    fun getProductData(productId: Long): LiveData<Product>

    /** Get the Bitmap for the product */
    suspend fun getImage(imageInfo: ImageInfo): Bitmap

    /** Get the 3d-Model for the product */
    suspend fun getModel(modelInfo: ModelInfo): ModelBuilder


    // --- Cart + Orders ---
    // --- Cart + Orders ---


    /** Get all orders for the user */
    fun getUserOrders(userId: Long): LiveData<List<Order>>

    /** Get instance of the current cart */
    fun useCart(): Cart
}