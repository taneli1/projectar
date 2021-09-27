package com.example.projectar.data.repository.intrfc

import androidx.lifecycle.LiveData
import com.example.projectar.data.room.entity.product.Product

interface ProductRepository {
    /**
     * Get all products
     */
    fun getProducts(): LiveData<List<Product>>

    /**
     * Get data for product id
     */
    fun getProduct(id: Long): LiveData<Product>

    fun insertProduct(product: Product): Long
}

