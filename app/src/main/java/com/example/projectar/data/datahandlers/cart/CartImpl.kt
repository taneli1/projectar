package com.example.projectar.data.datahandlers.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CartImpl(
    private var _selections: Map<Long, Int> = mutableMapOf(),
    private val selections: MutableLiveData<Map<Long, Int>> = MutableLiveData(_selections),
) : Cart {

    override fun addItem(productId: Long): Int {
        val copy = _selections.toMutableMap()

        when (val current = copy[productId]) {
            null -> copy[productId] = 1
            else -> copy[productId] = (current + 1)
        }

        updateMaps(copy)
        return copy[productId]!!
    }

    override fun removeItem(productId: Long): Int {
        val copy = _selections.toMutableMap()

        when (val current = copy[productId]) {
            null -> return 0
            0 -> return 0
            else -> copy[productId] = current - 1
        }

        updateMaps(copy)
        return copy[productId]!!
    }

    override fun getProductAmount(productId: Long): Int {
        return _selections[productId] ?: -1
    }

    override fun getCartTotal(): LiveData<Int> {
        return MutableLiveData(_selections.values.reduceOrNull { acc, i -> acc + i } ?: 0)
    }

    override fun getAll(): LiveData<Map<Long, Int>> = selections

    override fun removeUnselected() {
        val copy = _selections.toMutableMap()

        _selections.forEach {
            if (it.value == 0) {
                copy.remove(it.key)
            }
        }

        updateMaps(copy)
    }

    override fun clear() {
        updateMaps(mapOf())
    }

    // ----- Methods to make livedata map accessing more readable above --------
    // ----- Methods to make livedata map accessing more readable above --------
    // ----- Methods to make livedata map accessing more readable above --------

    private fun updateMaps(newMap: Map<Long, Int>) {
        _selections = newMap
        selections.postValue(newMap)
    }
}

