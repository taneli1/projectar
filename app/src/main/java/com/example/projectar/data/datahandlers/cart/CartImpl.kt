package com.example.projectar.data.datahandlers.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectar.data.datahandlers.order.handler.OrderHandler

class CartImpl(
    private val selections: MutableLiveData<MutableMap<Long, Int>> = MutableLiveData(mutableMapOf()),
) : Cart {

    override fun addItem(productId: Long): Int {
        when (val current = selections[productId]) {
            null -> selections[productId] = 1
            else -> selections[productId] = (current + 1)
        }

        return selections[productId]!!
    }

    override fun removeItem(productId: Long): Int {
        when (val current = selections[productId]) {
            null -> return 0
            0 -> return 0
            else -> selections[productId] = current - 1
        }

        return selections[productId]!!
    }

    override fun getProductAmount(productId: Long): LiveData<Int> {
        return MutableLiveData(selections[productId] ?: 0)
    }

    override fun getCartTotal(): LiveData<Int> {
        return MutableLiveData(selections.value?.values?.reduceOrNull { prev, next -> prev + next }
            ?: 0)
    }

    override fun getAll(): Map<Long, Int> {
        return selections.value ?: mutableMapOf()
    }

    // ----- Methods to make livedata map accessing more readable above --------
    // ----- Methods to make livedata map accessing more readable above --------
    // ----- Methods to make livedata map accessing more readable above --------

    private operator fun <T : MutableMap<Long, Int>> MutableLiveData<T>.set(
        productId: Long,
        value: Int
    ) {
        this.value?.set(productId, value)
    }

    private operator fun <T : Map<Long, Int>> MutableLiveData<T>.get(productId: Long): Int? {
        return this.value?.get(productId)
    }
}

