package com.example.projectar.ui.viewmodel

import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.lifecycle.*
import com.example.projectar.data.productdata.products.FakeProductList
import com.example.projectar.data.productdata.tags.ProductTags
import com.example.projectar.data.repository.intrfc.Filter
import com.example.projectar.data.repository.intrfc.ProductFilter
import com.example.projectar.data.repository.intrfc.ProductRepository
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.utils.ProductCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "ProductViewModel"

/**
 * Viewmodel which provides methods to access product data
 */
class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    // Default filter for products
    private val filter: MutableLiveData<ProductFilter> = MutableLiveData(Filter())

    // List of products with a filter applied
    val filteredProducts: LiveData<List<Product>> = filter.switchMap {
        productRepository.getProductsFiltered(it)
    }

    // List of all the products TODO remove later
    val products = productRepository.getProducts()

    // ---------------------- Methods ------------------------
    // ---------------------- Methods ------------------------
    // ---------------------- Methods ------------------------

    /**
     * Get all data for a single products from the database (Excluding tags)
     */
    fun getProductData(productId: Long) = productRepository.getProduct(productId)

    /**
     * Apply a filter for products to get from database
     */
    fun applyFilter(filter: ProductFilter) {
        this.filter.postValue(filter)
    }

    //
    //
    //
    // ---------------------- For testing purposes ------------------
    // ---------------------- For testing purposes ------------------
    //
    //
    //

    fun createProducts(db: ApplicationDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            ProductCreator.createProducts(db)
        }
    }

    class ProductViewModelFactory(private val productRepository: ProductRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductViewModel(productRepository) as T
        }
    }
}