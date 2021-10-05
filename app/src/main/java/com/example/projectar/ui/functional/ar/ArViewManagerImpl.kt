package com.example.projectar.ui.functional.ar

import android.util.Log
import com.example.projectar.data.datahandlers.assets.ARTAG
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

typealias ModelBuilderHandler = (modelBuilder: ModelBuilder, onComplete: (model: ModelRenderable) -> Unit) -> Unit

/**
 * Implementation for ArViewManager
 * @param vm ViewModel to get the product data from
 * @param _fragment A weak reference to the current ArFragment
 * @param buildModelHandler A function that can handle the building for the provided ModelBuilder.
 * (ModelBuilder.build() must be run on UI Thread - Defined by the library providing the object)
 */
class ArViewManagerImpl(
    private val vm: ProductViewModel,
    private val _fragment: WeakReference<ArFragment>,
    private val buildModelHandler: ModelBuilderHandler
) : ArViewManager {
    private val fragment get() = _fragment.get()!!

    private val job: Job = Job()
    private val scope = CoroutineScope(job)


    init {



        fragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            scope.launch {
                val product = vm.products.value?.find { it.data.id == it.data.id }

                // Get the model for the product
                val model = product?.model?.let { vm.getModel(it) }

                // Return if no model found
                model ?: run {
                    Log.d(ARTAG, "addModel: No model found")
                    return@launch
                }

                // If model was found, add it to the scene
                buildModelHandler(model) {
                    val anchor = hitResult!!.createAnchor()
                    //Creates a new anchorNode attaching it to anchor
                    val anchorNode = AnchorNode(anchor)
                    // Add anchorNode as root scene node's child
                    anchorNode.setParent(fragment.arSceneView.scene)
                    // Can be selected, rotated...
                    val viewNode = TransformableNode(fragment.transformationSystem)
                    viewNode.renderable = it
                    // Add viewNode as anchorNode's child
                    viewNode.setParent(anchorNode)
                    // Sets this as the selected node in the TransformationSystem
                    viewNode.select()
                }

            }
        }

    }


    // -------------------------------- Public --------------------------------
    // -------------------------------- Public --------------------------------

    override fun addModel(productId: Long) {

        Log.d(ARTAG, "addModel: called with product id $productId ")

        scope.launch {
            val product = vm.products.value?.find { it.data.id == it.data.id }

            // Get the model for the product
            val model = product?.model?.let { vm.getModel(it) }

            // Return if no model found
            model ?: run {
                Log.d(ARTAG, "addModel: No model found")
                return@launch
            }

            // If model was found, add it to the scene
            addModelToScene(model)
        }
    }

    override fun removeModel(productId: Long) {
    }


    // -------------------------------- Private --------------------------------
    // -------------------------------- Private --------------------------------


    private fun addModelToScene(modelBuilder: ModelBuilder) {
        Log.d(ARTAG, "addModelToScene")
        try {
            buildModelHandler(modelBuilder) {
                setToView(it)
            }
        } catch (e: Exception) {
            Log.e(ARTAG, "addModelToScene error: " + e.message)
        }
    }

    private fun setToView(model: ModelRenderable) {
//        val anchorNode = AnchorNode(anchor)
//        anchorNode.setParent(fragment.arSceneView.scene)
//        val mNode = TransformableNode(fragment.transformationSystem)
//        mNode.renderable = model
//        mNode.setParent(anchorNode)
//        mNode.select()
    }


}