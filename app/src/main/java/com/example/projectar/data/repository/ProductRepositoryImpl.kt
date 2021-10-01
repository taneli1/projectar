package com.example.projectar.data.repository

import androidx.lifecycle.LiveData
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.order.Order
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

    override fun getOrders(userId: Long): LiveData<List<Order>> =
        database.orderDao().getOrders(userId)

    override fun getProductTags(productId: Long): LiveData<List<Tag>> {
        return database.tagDao()
            .getAllTagsForProduct(productId)
    }

    override fun getAllTags(): LiveData<List<Tag>> {
        return database.tagDao()
            .getAllTags()
    }

    override fun insertProduct(product: Product): Long =
        database.productDao().insertProduct(product)

    override fun insertTag(tag: Tag): Long = database.tagDao().insert(tag)

    override fun insertOrder(order: Order): Long = database.orderDao().insert(order)


    override fun nukeDatabase() = database.nukeDao().nukeDb()
}