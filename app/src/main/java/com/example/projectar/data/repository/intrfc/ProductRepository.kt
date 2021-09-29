package com.example.projectar.data.repository.intrfc

import androidx.lifecycle.LiveData
import com.example.projectar.data.productdata.tags.ProductTags
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag

/**
 * Methods required for objects acting as Product Repository.
 */
interface ProductRepository {
    /*** Get all products*/
    fun getProducts(): LiveData<List<Product>>

    /*** Get product data for product id (Excluding tags)*/
    fun getProduct(id: Long): LiveData<Product>

    /** Get all tags linked to a product */
    fun getProductTags(productId: Long): LiveData<List<Tag>>

    /** Get all products that match the filters */
    fun getProductsFiltered(filter: ProductFilter): LiveData<List<Product>>

    // -- Insert --
    // -- Insert --

    fun insertProduct(product: Product): Long

    fun insertTag(tag: Tag): Long
}
