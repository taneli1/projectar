package com.example.projectar.ui.viewmodel

import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.lifecycle.*
import com.example.projectar.data.FakeProductList
import com.example.projectar.data.repository.intrfc.ProductRepository
import com.example.projectar.data.room.entity.product.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "ProductViewModel"

/**
 * Viewmodel which provides methods to access product data
 */
class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    val products = productRepository.getProducts()

    init {
        Log.d(
            TAG,
            "ViewModel Initialized"
        );
    }

    fun getProductData(productId: Long) = productRepository.getProduct(productId)

    // ---------------------- For testing purposes ------------------
    // ---------------------- For testing purposes ------------------

    fun createProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            FakeProductList.data.forEach {
                productRepository.insertProduct(it)
            }
        }
    }

    class ProductViewModelFactory(private val productRepository: ProductRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductViewModel(productRepository) as T
        }
    }
}