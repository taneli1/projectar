package com.example.projectar.data.managers.product

import android.graphics.Bitmap
import com.example.projectar.data.managers.assets.*
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo

/**
 * Class combining functionalities required by ProductManager interface.
 *
 * @param productRepository
 * @param imageManager A class that can provide image assets for the products
 * @param modelManager A class that can provide 3d models for the products
 */
class ProductManagerImpl(
    private val productRepository: ProductRepository,
    private val imageManager: ImageAssetManager,
    private val modelManager: ModelAssetManager,
) : ProductManager {

    // Product data getters
    override fun getProducts(pf: TagFilter) = productRepository.getProductsFiltered(pf)
    override fun getProduct(productId: Long) = productRepository.getProduct(productId)

    // Tag getters
    override fun getProductTags(productId: Long) = productRepository.getProductTags(productId)
    override fun getAllTags() = productRepository.getAllTags()

    // Product assets
    override fun getProductImage(imageInfo: ImageInfo): Bitmap = imageManager.getAsset(imageInfo)
    override fun getProductModel(modelInfo: ModelInfo): Model = modelManager.getAsset(modelInfo)

    // Cart methods?
}