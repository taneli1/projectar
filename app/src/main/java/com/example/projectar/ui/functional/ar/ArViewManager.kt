package com.example.projectar.ui.functional.ar

/**
 * Provides functionality for AR-View.
 * Manages the models in the view etc...
 */
interface ArViewManager {

    /** Add model for Product in the current active AR Scene*/
    fun addModel(productId: Long)

    /** Remove a model for a Product if it is currently in the scene */
    fun removeModel(productId: Long)
}