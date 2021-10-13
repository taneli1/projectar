package com.example.projectar.ui.functional.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.datahandlers.cart.Cart
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.order.Order
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.queryfilters.TagFilter

interface ProductViewModel {
    /** List of products matching the current provided filter */
    val products: LiveData<List<Product>>

    /** Set a filter for searched products */
    fun applyFilter(filter: TagFilter)

    fun getFilter(): TagFilter


    // --- Get different lists of products  ---
    // --- Get different lists of products  ---

    /**
     * Return a list of randomly selected products.
     *
     * @param generatedListId An identifier for the generated list. Method will return the
     * same list for the provided parameter each time it is called. (for the ViewModel Instance)
     * @param length Length of the list to generate. If list is already generated for the
     * provided id, this does nothing. Defaults to = 4. If longer than the list of products
     * its accessing, uses default value.
     * @param useSingleCategory If true, returns products only from the same category.
     * Keeps track of "used" categories. If there is an "unused" category does not create
     * duplicate category lists until every category has been used at least once. Logic is repeated
     * for every generated category list.
     * @return The list of products + the categories used for this list
     */
    fun getRandomListOfProducts(
        generatedListId: String,
        length: Int? = null,
        useSingleCategory: Boolean = false
    ): Pair<LiveData<List<Product>>, List<ProductTag>>

    // --- Product data ---
    // --- Product data ---

    /** Get all data for a product except its tags */
    fun getProductData(productId: Long): LiveData<Product>

    /** Get all products for a tag */
    fun getProductTags(productId: Long): LiveData<List<Tag>>

    /** Get all products matching tags */
    fun getProductsWithTags(tags: List<ProductTag>): LiveData<List<Product>>

    /** Get the Bitmap for the product */
    fun getImage(imageInfo: ImageInfo): Bitmap


    /** Get the 3d-Model for the product */
    fun getModel(modelInfo: ModelInfo): ModelBuilder


    // --- Cart + Orders ---
    // --- Cart + Orders ---


    /** Get all orders for the user */
    fun getUserOrders(userId: Long): LiveData<List<Order>>

    /** Get instance of the current cart */
    fun useCart(): Cart

    fun placeOrder()
}