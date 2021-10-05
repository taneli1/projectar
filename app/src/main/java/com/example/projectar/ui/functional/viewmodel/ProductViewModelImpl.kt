package com.example.projectar.ui.functional.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.datahandlers.product.ProductManager
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.order.Order
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.queryfilters.ProductFilter
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.room.utils.ProductCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Provides the functionality to access all the data the app needs.
 */
class ProductViewModelImpl(private val productManager: ProductManager) : ViewModel(),
    ProductViewModel {
    // Default filter for products
    private val filter: MutableLiveData<TagFilter> = MutableLiveData(ProductFilter())

    // List of products with a filter applied
    override val products: LiveData<List<Product>> = filter.switchMap {
        productManager.getProducts(it)
    }

    // ---------------------- Methods ------------------------

    override fun getProductData(productId: Long) = productManager.getProduct(productId)

    override fun applyFilter(filter: TagFilter) {
        this.filter.postValue(filter)
    }

    override suspend fun getImage(imageInfo: ImageInfo): Bitmap {
        return productManager.getProductImage(imageInfo)!!
    }

    override suspend fun getModel(modelInfo: ModelInfo): ModelBuilder {
        return productManager.getProductModel(modelInfo)!!
    }

    override fun getUserOrders(userId: Long): LiveData<List<Order>> {
        return productManager.getAllOrders(userId)
    }

    override fun useCart(): Cart = productManager.useCart()


    // -------------------------- TESTING --------------------------

    fun createProducts(db: ApplicationDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            ProductCreator.createProducts(db)
        }
    }

    class ProductViewModelFactory(private val productManager: ProductManager) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductViewModelImpl(productManager) as T
        }
    }
}