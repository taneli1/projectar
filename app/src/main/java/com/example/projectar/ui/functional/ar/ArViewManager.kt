package com.example.projectar.ui.functional.ar

/**
 * Provides functionality for AR-View.
 * Manages the models in the view etc...
 */
interface ArViewManager {
    /**
     * Set a product model to be currently selected.
     *
     * Tapping a plane after selecting a model adds it to the ar scene.
     *
     * Selected model gets reset after tapping a plane, so this
     * method needs to be called again to add another model.
     */
    fun setModelSelected(productId: Long)
}