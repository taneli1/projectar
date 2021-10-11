package com.example.projectar.ui.functional.ar

import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.projectar.R
import com.example.projectar.data.datahandlers.assets.ARTAG
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.utils.StringUtils
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
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
 * @param buildModelHandler A function that can handle the building of the model for the
 * provided ModelBuilder.
 * (ModeRenderable.Builder.build() must be run on UI Thread - Defined by the library providing the obj.)
 */
class ArViewManagerImpl(
    private val vm: ProductViewModel,
    private val _fragment: WeakReference<ArFragment>,
    private val buildModelHandler: ModelBuilderHandler
) : ArViewManager {
    private val fragment get() = _fragment.get()!!

    private val job: Job = Job()
    private val scope = CoroutineScope(job)

    // Currently selected product ModelBuilder by the user, with product data saved
    private var selectedModel: Pair<Product, ModelBuilder>? = null

    // Nodes, which have the additional layout open
    private val nodesWithLayoutOpen = mutableListOf<Node>()

    init {
        fragment.setOnTapArPlaneListener { hitResult, _, _ ->
            scope.launch {
                val s = selectedModel
                if (s != null) {
                    addModelToScene(hitResult, s)
                }
            }
        }
    }


    // -------------------------------- Public --------------------------------
    // -------------------------------- Public --------------------------------

    /**
     * Set a model as "selected"
     */
    override fun setModelSelected(productId: Long) {
        scope.launch {
            val product = vm.products.value?.find { it.data.id == it.data.id }

            // Get the model for the product
            val model = product?.model?.let { vm.getModel(it) }

            // Return if no model found
            model ?: run {
                Log.d(ARTAG, "addModel: No model found")
                return@launch
            }

            // If model was found, save it to class field
            selectedModel = Pair(product, model)
        }
    }


    // -------------------------------- Private --------------------------------
    // -------------------------------- Private --------------------------------


    private fun addModelToScene(hitResult: HitResult, data: Pair<Product, ModelBuilder>) {
        try {
            buildModelHandler(data.second) {
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(fragment.arSceneView.scene)

                val viewNode = TransformableNode(fragment.transformationSystem)
                viewNode.apply {
                    scaleController.maxScale = 0.31f
                    scaleController.minScale = 0.3f
                    renderable = it
                    setParent(anchorNode)
                    select()

                    setOnTapListener { hitTestResult, _ ->
                        val node = hitTestResult.node
                        if (node != null) {
                            renderLayout(node, data.first)
                        }
                    }
                }

                // Clear the selected model
                selectedModel = null
            }
        } catch (e: Exception) {
            Log.e(ARTAG, "addModelToScene error: " + e.message)
        }
    }

    /**
     * Render additional layout on top of the node, showing
     * specific information for the product.
     * (+ remove model from scene button)
     */
    private fun renderLayout(modelNode: Node, product: Product) {
        // Don't render multiple layouts for single model
        if (nodesWithLayoutOpen.contains(modelNode)) {
            return
        }
        nodesWithLayoutOpen.add(modelNode)


        val viewNode = Node().apply {
            setParent(modelNode)
            isEnabled = false
            localPosition = Vector3(0.0f, 1.0f, 0.0f)
        }

        ViewRenderable.builder()
            .setView(fragment.context, R.layout.view_ar_model_actions)
            .build()
            .thenAccept { viewRenderable ->
                viewNode.apply {
                    renderable = viewRenderable
                    isEnabled = true

                    // Apply specific information to the product
                    viewRenderable.view.findViewById<TextView>(R.id.view_ar_product_title)
                        .text = product.data.title
                    viewRenderable.view.findViewById<TextView>(R.id.view_ar_product_price)
                        .text = StringUtils.formatFloat(product.data.price) + "€"

                    // Remove the entire model when delete button is pressed
                    viewRenderable.view.findViewById<Button>(R.id.view_ar_delete_button).setOnClickListener {
                        removeNode(modelNode)
                    }


                    // Remove the layout onTap
                    setOnTapListener { hitRes, _ ->
                        val resNode = hitRes.node ?: return@setOnTapListener

                        // Get the node of the model to which this view is attached to
                        // and remove this view from the nodes children.
                        fragment.arSceneView.scene.children.find { it == modelNode }?.removeChild(resNode)
                        resNode.setParent(null)
                        resNode.renderable = null
                        nodesWithLayoutOpen.remove(modelNode)
                    }
                }
            }
    }

    private fun removeNode(node: Node) {
        fragment.arSceneView.scene.removeChild(node);
        node.setParent(null);
        node.renderable = null
    }
}


