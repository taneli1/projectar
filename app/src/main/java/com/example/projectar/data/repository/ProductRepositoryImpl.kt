package com.example.projectar.data.repository

import androidx.lifecycle.LiveData
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag

/**
 * Repository for product data.
 */
class ProductRepositoryImpl(private val database: ApplicationDatabase) : ProductRepository {

    override fun getProduct(id: Long): LiveData<Product> = database.productDao()
        .getProduct(id)

    override fun getProducts(): LiveData<List<Product>> = database.productDao()
        .getAllProducts()

    override fun getProductsFiltered(filter: TagFilter): LiveData<List<Product>> {
        return database.tagDao()
            .getProductsFiltered(filter)
    }

    override fun getProductTags(productId: Long): LiveData<List<Tag>> {
        return database.tagDao()
            .getAllTagsForProduct(productId)
    }

    override fun getAllTags(): LiveData<List<Tag>> {
        return database.tagDao()
            .getAllTags()
    }

    override fun insertProduct(product: Product): Long = database.productDao()
        .insertProduct(product)

    override fun insertTag(tag: Tag): Long = database.tagDao()
        .insert(tag)
}