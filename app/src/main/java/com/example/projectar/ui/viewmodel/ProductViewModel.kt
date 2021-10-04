package com.example.projectar.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.example.projectar.data.datahandlers.product.ProductManager
import com.example.projectar.data.room.queryfilters.ProductFilter
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.utils.ProductCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ProductViewModel"

/**
 * Viewmodel which provides methods to access product data
 */
class ProductViewModel(private val productManager: ProductManager) : ViewModel() {
    // Default filter for products
    private val filter: MutableLiveData<TagFilter> = MutableLiveData(ProductFilter())

    // List of products with a filter applied
    val filteredProducts: LiveData<List<Product>> = filter.switchMap {
        productManager.getProducts(it)
    }

    // List of all the products TODO remove later
    val products = productManager.getProducts(ProductFilter())

    // ---------------------- Methods ------------------------
    // ---------------------- Methods ------------------------
    // ---------------------- Methods ------------------------

    /**
     * Get all data for a single products from the database (Excluding tags)
     */
    fun getProductData(productId: Long) = productManager.getProduct(productId)

    /**
     * Apply a filter for products to get from database
     */
    fun applyFilter(filter: TagFilter) {
        this.filter.postValue(filter)
    }

    fun getImage(imageInfo: ImageInfo): Bitmap {
        return productManager.getProductImage(imageInfo)
    }

    fun getCart() = productManager.useCart()

    //
    //
    // ---------------------- For testing purposes ------------------
    // ---------------------- For testing purposes ------------------
    //
    //

    fun createProducts(db: ApplicationDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            ProductCreator.createProducts(db)
        }
    }

    class ProductViewModelFactory(private val productManager: ProductManager) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductViewModel(productManager) as T
        }
    }
}