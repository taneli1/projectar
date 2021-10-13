package com.example.projectar.ui.functional.ar

import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.example.projectar.R
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

/**
 * Function that takes ModelBuilder, calls the build() method on it, and calls the onComplete
 * function with the model it got from the build() method. (must happen on UI-Thread)
 */
typealias ModelBuilderFunc =
        (modelBuilder: ModelBuilder, onComplete: (model: ModelRenderable) -> Unit) -> Unit

/**
 * Implementation of ArViewManager.
 *
 * @param vm ViewModel to get the product data from
 * @param _fragment A weak reference to the current ArFragment
 * @param buildModelFunc A function that can handle the building of the model for the
 * provided ModelBuilder.
 * (ModeRenderable.Builder.build() must be run on UI Thread - defined by the library)
 */
class ProductArViewManager(
    private val vm: ProductViewModel,
    private val _fragment: WeakReference<ArFragment>,
    private val buildModelFunc: ModelBuilderFunc
) // Using ProductIds (Long) to figure out which models are targeted.
    : ArViewManager<Long> {
    override val modelSelected: MutableLiveData<Long?> = MutableLiveData(null)
    override val renderedModels: MutableLiveData<Map<Long, Int>> = MutableLiveData(mapOf())

    private val fragment get() = _fragment.get()!!
    private val job: Job = Job()
    private val scope = CoroutineScope(job)

    // Currently selected ModelBuilder with product data
    private var selectedModel: Pair<Product, ModelBuilder>? = null

    // Nodes, which currently have the additional layout open
    private val nodesWithLayoutOpen = mutableListOf<Node>()

    // Has the guide been shown on the first model render
    private var guideShown = false

    // (ProductId - List<Node>) Nodes added to the scene mapped to corresponding product IDs
    private val nodeMap = mutableMapOf<Long, MutableList<Node>>()

    init {
        // Add currently selected model to scene on tap
        fragment.setOnTapArPlaneListener { hitResult, _, _ ->
            scope.launch {
                val m = selectedModel
                if (m != null) {
                    addModelToScene(hitResult, m)
                }
            }
        }
    }

    // -------------------------------- Public --------------------------------
    // -------------------------------- Public --------------------------------

    /**
     * Set a product as selected
     */
    override fun setModelSelected(data: Long) {
        scope.launch {
            val product = vm.products.value?.find { it.data.id == data }

            // Get the model builder for the product
            val model = product?.model?.let { vm.getModel(it) }

            // Return if no model found
            model ?: run {
                return@launch
            }

            // If model was found, save it to class field
            selectedModel = Pair(product, model)
            // Set the livedata value to passed product id, to tell the UI the model is ready
            modelSelected.postValue(data)
        }
    }

    override fun removeModel(data: Long) {
        val listOfModelNodes = nodeMap.getOrDefault(data, null) ?: return

        val node = listOfModelNodes.removeLast()
        removeModelNode(data, node)
    }

    // -------------------------------- Private --------------------------------
    // -------------------------------- Private --------------------------------

    private fun addModelToScene(hitResult: HitResult, data: Pair<Product, ModelBuilder>) {
        try {
            buildModelFunc(data.second) {
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor).apply {
                    setParent(fragment.arSceneView.scene)
                }

                val viewNode = TransformableNode(fragment.transformationSystem).apply {
                    scaleController.maxScale = 1.001f
                    scaleController.minScale = 1f
                    renderable = it
                    setParent(anchorNode)
                    select()

                    // Show additional layout on top of model when clicked
                    setOnTapListener { hitTestResult, _ ->
                        val node = hitTestResult.node
                        if (node != null) {
                            renderLayout(node, data.first)
                        }
                    }
                }

                displayGuideIfFirst(viewNode)
                clearFields()
                registerModelNode(data.first.data.id, viewNode)
            }
        } catch (e: Exception) {
            Log.e("DEBUG", "addModelToScene error: " + e.message)
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

                    setViewData(viewRenderable, product, modelNode)

                    // Remove this layout onTap
                    setOnTapListener { hitRes, _ ->
                        val resNode = hitRes.node ?: return@setOnTapListener
                        removeNestedNode(modelNode, resNode)
                        nodesWithLayoutOpen.remove(modelNode)
                    }
                }
            }
    }

    // -------------------------- Node handling -------------------------------------
    // -------------------------- Node handling -------------------------------------

    private fun buildNodeAboveAnother(parent: Node): Node {
        return Node().apply {
            setParent(parent)
            isEnabled = false
            localPosition = Vector3(0.0f, 1.0f, 0.0f)
        }
    }

    /**
     * Removes a node which contains the model of a product
     */
    private fun removeModelNode(productId: Long, node: Node) {
        fragment.arSceneView.scene.removeChild(node);
        node.setParent(null);
        node.renderable = null
        unRegisterModelNode(productId, node)
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

    // -------------------------- Guide nodes -------------------------------------
    // -------------------------- Guide nodes -------------------------------------

    /**
     * Render small guide on top if this is the first model,
     */
    private fun displayGuideIfFirst(parent: Node) {
        if (!guideShown) {
            renderGuide(parentNode = parent)
            guideShown = true
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

    // -------------------------- Class related -------------------------------------
    // -------------------------- Class related -------------------------------------

    /**
     * Call when a model has been set to the scene
     */
    private fun clearFields() {
        // Clear the selected model to prevent accidental model additions to the scene
        selectedModel = null
        modelSelected.postValue(null)
    }

    /**
     * Call to register node as rendered
     */
    private fun registerModelNode(productId: Long, node: Node) {
        val nodeList = nodeMap.getOrDefault(productId, mutableListOf())
        nodeList.add(node)
        nodeMap[productId] = nodeList

        val nodeCountMap = nodeMap.mapValues {
            it.value.size
        }
        Log.d("test", "registerModelNode: $nodeCountMap")
        renderedModels.postValue(nodeCountMap)

        Log.d("TEST", "registerModelNode: ${nodeCountMap.values}")
    }

    /**
     * Call to unregister node from rendered list
     */
    private fun unRegisterModelNode(productId: Long, unregisterNode: Node) {
        val nodeList = nodeMap.getOrDefault(productId, null)
        nodeList?.remove(unregisterNode)

        val nodeCountMap = nodeMap.mapValues {
            it.value.size
        }
        renderedModels.postValue(nodeCountMap)
    }

    // -------------------------- Other -------------------------------------
    // -------------------------- Other -------------------------------------

    /**
     * Set the view layout data + delete functionality
     */
    private fun setViewData(viewRenderable: ViewRenderable, product: Product, modelNode: Node) {
        viewRenderable.view.apply {
            // Apply product info
            findViewById<TextView>(R.id.view_ar_product_title)
                .text = product.data.title
            findViewById<TextView>(R.id.view_ar_product_price)
                .text = fragment.context?.getString(
                R.string.price_string,
                StringUtils.formatFloat(product.data.price)
            )

            // Remove the entire model when delete button is pressed
            findViewById<Button>(R.id.view_ar_delete_button).setOnClickListener {
                removeModelNode(product.data.id, modelNode)
            }
        }
    }
}


