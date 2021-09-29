package com.example.projectar.data.repository

import androidx.lifecycle.LiveData
import com.example.projectar.data.repository.intrfc.ProductFilter
import com.example.projectar.data.repository.intrfc.ProductRepository
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag

/**
 * Repository for product data.
 */
class ProductRepositoryImpl(private val database: ApplicationDatabase) : ProductRepository {

    override fun getProducts(): LiveData<List<Product>> = database.productDao()
        .getAllProducts()

    override fun getProduct(id: Long): LiveData<Product> = database.productDao()
        .getProduct(id)

    override fun getProductTags(productId: Long): LiveData<List<Tag>> {
        return database.tagDao()
            .getAllTagsForProduct(productId)
    }

    override fun getProductsFiltered(filter: ProductFilter): LiveData<List<Product>> {
        return database.tagDao()
            .getProductsFiltered(filter)
    }

    // -- Insert --
    // -- Insert --

    override fun insertProduct(product: Product): Long = database.productDao()
        .insertProduct(product)

    override fun insertTag(tag: Tag): Long = database.tagDao()
        .insert(tag)
}