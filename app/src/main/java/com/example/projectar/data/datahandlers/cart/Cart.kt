package com.example.projectar.data.datahandlers.cart

import androidx.lifecycle.LiveData

/**
 * Defines properties for Cart implementations of the app.
 * Used to manage user selections for different products.
 */
interface Cart {
    /**
     * Adds an item to the cart
     * @return Current amount of the item in the cart
     */
    fun addItem(productId: Long): Int

    /**
     * Removes an item from the cart, if amount is not 0.
     * @return Current amount of the item in the cart
     * */
    fun removeItem(productId: Long): Int

    fun getProductAmount(productId: Long): LiveData<Int>

    /** Total amount of products in the cart */
    fun getCartTotal(): LiveData<Int>

    fun getAll(): Map<Long, Int>
}