package com.example.projectar.ui.functional.ar

import android.graphics.Point
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projectar.data.datahandlers.assets.ARTAG
import com.example.projectar.data.datahandlers.assets.Model
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.collision.Plane
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

typealias FunctionModelBuilder = (model: Model, onComplete: (model: ModelRenderable) -> Unit) -> Unit

class ArViewManagerImpl(
    private val vm: ProductViewModel,
    private val _fragment: WeakReference<ArFragment>,
    private val buildModel: FunctionModelBuilder
) : ArViewManager {
    private var _loading = false
    override val loading: MutableLiveData<Boolean> = MutableLiveData(_loading)
    private val fragment get() = _fragment.get()!!

    private val job: Job = Job()
    private val scope = CoroutineScope(job)
    // -------------------------------- Public --------------------------------
    // -------------------------------- Public --------------------------------

    override fun addModel(productId: Long) {
        if (_loading) {
            return
        }
        setLoading(true)

        scope.launch {

            val product = vm.products.value?.find { it.data.id == it.data.id }

            Log.d(ARTAG, "addModel: DEBUGTEST," + product.toString())
            // Get the model for the product
            val model = product?.model?.let { vm.getModel(it) }

            // Return if no model found
            model ?: run {
                Log.d(ARTAG, "addModel: No model found")
                setLoading(false)
                return@launch
            }

            // If model was found, add it to the scene
            addModelToScene(model)
            setLoading(false)
        }
    }

    override fun removeModel(productId: Long) {
        if (_loading) {
            return
        }
        setLoading(true)
    }


    // -------------------------------- Private --------------------------------
    // -------------------------------- Private --------------------------------

    private fun setLoading(bool: Boolean) {
        _loading = bool
        loading.postValue(_loading)
    }

    private fun addModelToScene(model: Model) {

        Log.d(ARTAG, "GOTHERE")

        try {
            buildModel(model) {
                setToView(it)
            }
        } catch (e: Exception) {
        }
    }

    private fun setToView(model: ModelRenderable) {
        val frame = fragment.arSceneView?.arFrame
        val pt = Point(100, 100)

        val hits = frame?.hitTest(pt.x.toFloat(), pt.y.toFloat())
        if (hits != null) {
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane) {
                    val anchor = hit!!.createAnchor()
                    val anchorNode = AnchorNode(anchor)
                    anchorNode.setParent(fragment.arSceneView.scene)
                    val mNode =
                        TransformableNode(fragment.transformationSystem)
                    mNode.renderable = model
                    mNode.setParent(anchorNode)
                    mNode.select()
                    break
                }
            }
        }
    }
}