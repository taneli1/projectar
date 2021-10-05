package com.example.projectar.ui.functional.ar

import androidx.lifecycle.LiveData

/**
 * Provides functionality for AR-View.
 * Manages the models in the view etc...
 */
interface ArViewManager {
    /** Manager working on something? */
    val loading: LiveData<Boolean>

    /** Add model for Product in the current active AR Scene*/
    fun addModel(productId: Long)

    /** Remove a model for a Product if it is currently in the scene */
    fun removeModel(productId: Long)
}