package com.example.projectar.data.managers.product

import androidx.lifecycle.LiveData
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag

/**
 * Interface with methods to use different functionalities of the application
 * from one place. Used to provide easier access to everything from ViewModels.
 */
interface ProductManager {
    fun getProducts(pf: TagFilter): LiveData<List<Product>>
    fun getProduct(productId: Long): LiveData<Product>

    fun getProductTags(productId: Long): LiveData<List<Tag>>
    fun getAllTags(): LiveData<List<Tag>>
}
