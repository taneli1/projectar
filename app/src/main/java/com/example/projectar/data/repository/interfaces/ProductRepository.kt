package com.example.projectar.data.repository.interfaces

import androidx.lifecycle.LiveData
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.queryfilters.TagFilter

/**
 * Methods required for objects acting as Product Repository.
 */
interface ProductRepository {
    /*** Get all products*/
    fun getProducts(): LiveData<List<Product>>

    /*** Get product data for product id (Excluding tags)*/
    fun getProduct(id: Long): LiveData<Product>

    fun getProductTags(productId: Long): LiveData<List<Tag>>
    fun getAllTags(): LiveData<List<Tag>>

    /** Get all products that match the filters */
    fun getProductsFiltered(filter: TagFilter): LiveData<List<Product>>

    // -- Insert --
    fun insertProduct(product: Product): Long
    fun insertTag(tag: Tag): Long
}
