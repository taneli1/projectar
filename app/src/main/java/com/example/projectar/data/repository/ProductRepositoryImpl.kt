package com.example.projectar.data.repository

import androidx.lifecycle.LiveData
import com.example.projectar.data.repository.intrfc.ProductRepository
import com.example.projectar.data.room.dao.ProductDao
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product

/**
 * Repository for application data
 */
class ProductRepositoryImpl(private val database: ApplicationDatabase) : ProductRepository {
    override fun getProducts(): LiveData<List<Product>> = database.productDao()
        .getAllProducts()

    override fun getProduct(id: Long): LiveData<Product> = database.productDao()
        .getProduct(id)

    override fun insertProduct(product: Product): Long = database.productDao()
        .insertProduct(product)
}