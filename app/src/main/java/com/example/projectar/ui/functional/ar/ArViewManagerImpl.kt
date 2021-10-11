package com.example.projectar.ui.functional.ar

import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
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
 * (ModeRenderable.Builder.build() must be run on UI Thread - defined by the library)
 */
class ArViewManagerImpl(
    private val vm: ProductViewModel,
    private val _fragment: WeakReference<ArFragment>,
    private val buildModelHandler: ModelBuilderHandler
) : ArViewManager {
    override val modelSelected: MutableLiveData<Long?> = MutableLiveData(null)
    private val fragment get() = _fragment.get()!!

    private val job: Job = Job()
    private val scope = CoroutineScope(job)

    // Currently selected product ModelBuilder by the user, with product data saved
    private var selectedModel: Pair<Product, ModelBuilder>? = null

    // Nodes, which have the additional layout open
    private val nodesWithLayoutOpen = mutableListOf<Node>()

    // If the model added is the first one, show a guide on top of the it
    private var guideShown = false

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
            // Set the livedata value to passed product id, to tell the UI the model is ready
            modelSelected.postValue(productId)
        }
    }


    // -------------------------------- Private --------------------------------
    // -------------------------------- Private --------------------------------


    private fun addModelToScene(hitResult: HitResult, data: Pair<Product, ModelBuilder>) {
        try {
            buildModelHandler(data.second) {
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor).apply {
                    setParent(fragment.arSceneView.scene)
                }

                val viewNode = TransformableNode(fragment.transformationSystem).apply {
                    scaleController.maxScale = 0.31f
                    scaleController.minScale = 0.3f
                    renderable = it
                    setParent(anchorNode)
                    select()

                    // Show layout on top of model when clicked
                    setOnTapListener { hitTestResult, _ ->
                        val node = hitTestResult.node
                        if (node != null) {
                            renderLayout(node, data.first)
                        }
                    }
                }

                if (!guideShown) {
                    renderGuide(parentNode = viewNode)
                    guideShown = true
                }

                // Clear the selected model to prevent accidental model additions to the scene
                selectedModel = null
                modelSelected.postValue(null)
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

        val viewNode = buildNodeAboveAnother(modelNode)

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
                        .text = fragment.context?.getString(R.string.price_string, StringUtils.formatFloat(product.data.price))

                    // Remove the entire model when delete button is pressed
                    viewRenderable.view.findViewById<Button>(R.id.view_ar_delete_button).setOnClickListener {
                        removeSceneNode(modelNode)
                    }


                    // Remove this layout onTap
                    setOnTapListener { hitRes, _ ->
                        val resNode = hitRes.node ?: return@setOnTapListener

                        removeNestedNode(modelNode, resNode)
                        nodesWithLayoutOpen.remove(modelNode)
                    }
                }
            }
    }

    // Render a guide text above a node, telling to tap the model
    private fun renderGuide(parentNode: Node) {
        val viewNode = buildNodeAboveAnother(parentNode)

        ViewRenderable.builder()
            .setView(fragment.context, R.layout.view_ar_text)
            .build()
            .thenAccept { viewRenderable ->
                viewNode.apply {
                    renderable = viewRenderable
                    isEnabled = true

                    // Set the guide text
                    viewRenderable.view.findViewById<TextView>(R.id.ar_view_text_field)
                        .text = fragment.context?.getString(R.string.ar_view_tap_guide)
                }
            }

        // Remove the guide when something else is touched
        fragment.arSceneView.scene.setOnTouchListener { _, _ ->
            removeNestedNode(parentNode, viewNode)
            false
        }
    }

    private fun buildNodeAboveAnother(parent: Node): Node {
        return Node().apply {
            setParent(parent)
            isEnabled = false
            localPosition = Vector3(0.0f, 1.0f, 0.0f)
        }
    }

    /**
     * Removes a node which is a direct child of the scene
     */
    private fun removeSceneNode(node: Node) {
        fragment.arSceneView.scene.removeChild(node);
        node.setParent(null);
        node.renderable = null
    }

    /**
     * Remove a nested node from the scene
     * @param parent direct child of the scene
     * @param child child of the parent
     */
    private fun removeNestedNode(parent: Node, child: Node) {
        fragment.arSceneView.scene.children.find { it == parent }?.removeChild(child)
        child.setParent(null)
        child.renderable = null
    }
}


