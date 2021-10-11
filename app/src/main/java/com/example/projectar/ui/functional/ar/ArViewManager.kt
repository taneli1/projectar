package com.example.projectar.ui.functional.ar

import androidx.lifecycle.MutableLiveData

/**
 * Provides functionality for AR-View.
 * Manages the models in the view etc...
 */
interface ArViewManager {

    /**
     * If a model is ready to be set to the scene, this field
     * emits the value back which was set from setModelSelected().
     * If there is no model selected, value in livedata is null.
     */
    val modelSelected: MutableLiveData<Long?>

    /**
     * Set a product model to be currently selected.
     *
     * Tapping a plane after selecting a model adds it to the ar scene.
     *
     * Selected model gets reset after tapping a plane, so this
     * method needs to be called again to add another model.
     * (to prevent accidental model additions)
     */
    fun setModelSelected(productId: Long)
}