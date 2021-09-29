package com.example.projectar.data.managers.product

import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.repository.interfaces.ProductRepository

/**
 * Class combining functionalities required by ProductManager interface.
 */
class ProductManagerImpl(private val productRepository: ProductRepository) : ProductManager {

    // Product data getters
    override fun getProducts(pf: TagFilter) = productRepository.getProductsFiltered(pf)
    override fun getProduct(productId: Long) = productRepository.getProduct(productId)

    // Tag getters
    override fun getProductTags(productId: Long) = productRepository.getProductTags(productId)
    override fun getAllTags() = productRepository.getAllTags()

    // Cart methods?

    // AssetManager methods?
}