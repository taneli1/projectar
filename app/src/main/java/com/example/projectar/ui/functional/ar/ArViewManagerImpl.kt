package com.example.projectar.ui.functional.ar

import android.graphics.Point
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projectar.data.datahandlers.assets.ARTAG
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.google.ar.core.Plane
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
        val frame = fragment.arSceneView?.arFrame
        val pt = Point((fragment.view?.width ?: 0) / 2, (fragment.view?.height ?: 0) / 2)

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