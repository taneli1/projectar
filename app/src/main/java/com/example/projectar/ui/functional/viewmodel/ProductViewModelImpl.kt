package com.example.projectar.ui.functional.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.datahandlers.product.ProductManager
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.order.Order
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.queryfilters.ProductFilter
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.utils.TagUtils
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

    // Generated lists of products + tags used for that list
    private val generatedProductLists =
        mutableMapOf<String, Pair<LiveData<List<Product>>, List<ProductTag>>>()

    private var unusedTagList = TagUtils.getAllTags().toMutableList()

    // ---------------------- Methods ------------------------

    override fun getProductData(productId: Long) = productManager.getProduct(productId)


    override fun getProductsWithTags(tags: List<ProductTag>): LiveData<List<Product>> =
        productManager.getProducts(ProductFilter(tags = tags))


    override fun applyFilter(filter: TagFilter) {
        this.filter.postValue(filter)
        Log.d("Filter: ", filter.searchTerm + " " + filter.sortBy)
    }

    override fun getFilter(): TagFilter {
        return this.filter.value ?: ProductFilter()
    }

    override fun getRandomListOfProducts(
        generatedListId: String,
        length: Int?,
        useSingleCategory: Boolean
    ): Pair<LiveData<List<Product>>, List<ProductTag>> {
        if (generatedProductLists.containsKey(generatedListId)) {
            val data = generatedProductLists[generatedListId]!!
            if (data.first.value?.size != 0) {
                return data
            }
        }

        viewModelScope.launch(Dispatchers.Default) {
            // List for the identifier not yet generated, do now in background, return null
            val generated = mutableListOf<Product>()

            val tags = if (useSingleCategory)
                listOf(getRandomTag()) else TagUtils.getAllTags()

            val filter = ProductFilter(
                tags = tags
            )
            Log.d("TAG", "getRandomListOfProducts: $filter")
            val productPool = productManager.getProductsNotLive(filter).toMutableList().also {
                it.shuffle()
            }

            val len = if (length == null || length > productPool.size) {
                productPool.size
            } else length

            for (i in 0 until len) {
                generated.add(productPool[i])
            }

            Log.d("TAG", "Saved tags: $tags")
            generatedProductLists[generatedListId] = Pair(MutableLiveData(generated), filter.tags)
            Log.d("TAG", "Getting after: ${generatedProductLists[generatedListId]!!.second}")

        }

        // Return empty data while initializing
        return Pair(MutableLiveData(listOf()), listOf())
    }

    override fun getImage(imageInfo: ImageInfo): Bitmap {
        return productManager.getProductImage(imageInfo)!!
    }

    override fun getModel(modelInfo: ModelInfo): ModelBuilder {
        return productManager.getProductModel(modelInfo)!!
    }

    override fun getUserOrders(userId: Long): LiveData<List<Order>> {
        return productManager.getAllOrders(userId)
    }

    override fun useCart(): Cart = productManager.useCart()

    override fun placeOrder() {
        productManager.placeOrder()
    }

    // -------------------------- PRIVATE --------------------------
    // -------------------------- PRIVATE --------------------------

    /**
     * Gets a random "unused" tag.
     * @see getRandomListOfProducts - useSingleCategory
     */
    private fun getRandomTag(): ProductTag {
        val tag = unusedTagList.randomOrNull()
        // Case where every tag has been used, reset the list
        return if (tag == null) {
            unusedTagList = TagUtils.getAllTags().toMutableList()
            val t = unusedTagList.random()
            unusedTagList.remove(t)
            t
        } else {
            unusedTagList.remove(tag)
            tag
        }
    }

    /**
     * Suppress UNCHECKED_CAST here, since the cast to T will never fail, and
     * it must be done to override the default create method.
     */
    @Suppress("UNCHECKED_CAST")
    class ProductViewModelFactory(private val productManager: ProductManager) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductViewModelImpl(productManager) as T
        }
    }
}