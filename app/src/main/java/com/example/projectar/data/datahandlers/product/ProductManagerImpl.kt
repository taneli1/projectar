package com.example.projectar.data.datahandlers.product

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.projectar.data.datahandlers.assets.ImageAssetManager
import com.example.projectar.data.datahandlers.assets.ModelAssetManager
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.datahandlers.order.builder.OrderBuilder
import com.example.projectar.data.datahandlers.order.handler.OrderHandler
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.order.Order
import com.example.projectar.data.room.queryfilters.TagFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Class combining functionalities required by ProductManager interface.
 *
 * @param productRepository
 * @param imageManager A class that can provide image assets for the products
 * @param modelManager A class that can provide 3d models for the products
 * @param cart A Cart implementation
 * @param orderHandler A class handling the orders after user wants to place it
 * @param orderBuilder Class building the orders with all the required data
 */
class ProductManagerImpl(
    private val productRepository: ProductRepository,
    private val imageManager: ImageAssetManager,
    private val modelManager: ModelAssetManager,
    private val cart: Cart,
    private val orderHandler: OrderHandler,
    private val orderBuilder: OrderBuilder
) : ProductManager {
    private val job = Job()
    private val scope = CoroutineScope(job)

    // Product data getters
    override fun getProducts(pf: TagFilter) = productRepository.getProductsFiltered(pf)
    override fun getProduct(productId: Long) = productRepository.getProduct(productId)

    // Tag getters
    override fun getProductTags(productId: Long) = productRepository.getProductTags(productId)
    override fun getAllTags() = productRepository.getAllTags()

    // Product assets
    override fun getProductImage(imageInfo: ImageInfo): Bitmap? =
        imageManager.getAsset(imageInfo)

    override fun getProductModel(modelInfo: ModelInfo): ModelBuilder? =
        modelManager.getAsset(modelInfo)

    // Orders
    override fun getAllOrders(userId: Long): LiveData<List<Order>> =
        productRepository.getOrders(userId)

    // Cart
    override fun useCart(): Cart = cart
    override fun placeOrder() {
        scope.launch {
            val order = orderBuilder.buildOrder(cart.getAll().value ?: mapOf())
            orderHandler.placeOrder(order)
            cart.clear()
        }
    }
}
