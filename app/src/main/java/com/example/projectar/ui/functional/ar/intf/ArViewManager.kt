package com.example.projectar.ui.functional.ar.intf

import androidx.lifecycle.MutableLiveData

/**
 * Provides functionality for AR-View.
 * Manages the models in the view etc...
 *
 * T - Type of value which is used to differentiate what models to render/what is rendered etc.
 */
interface ArViewManager<T> {

    /**
     * What models are currently rendered and visible in the scene. (+ count of that model)
     */
    val renderedModels: MutableLiveData<Map<T, Int>>

    /**
     * If a model is ready to be set to the scene, this field
     * emits the value back which was set from setModelSelected().
     * If there is no model selected, value in livedata is null.
     */
    val modelSelected: MutableLiveData<T?>

    /**
     * Set a product to be currently selected.
     * Tapping a plane after selecting a model adds it to the ar scene.
     *
     * Selected model gets reset after tapping a plane, so this
     * method needs to be called again to add another model.
     * (to prevent accidental model additions)
     */
    fun setModelSelected(data: T)

    /**
     * Remove a model from scene.
     *
     * If there are multiple models listed under the parameter, removes the last added model.
     */
    fun removeModel(data: T)

    fun saveBundle()
}