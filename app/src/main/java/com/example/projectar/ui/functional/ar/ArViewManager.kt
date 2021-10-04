package com.example.projectar.ui.functional.ar

/**
 * Provides functionality for AR-View.
 * Manages the models in the view etc...
 */
interface ArViewManager {
    fun addModel(productId: Long)
    fun removeModel(productId: Long)
}