package com.example.projectar.data.datahandlers.assets

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.projectar.data.room.entity.file.ModelInfo
import com.google.ar.sceneform.rendering.ModelRenderable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import java.lang.ref.WeakReference

const val ARTAG = "ARTAG"

/**
 * Get 3dModels from assets
 */
class ResourceModelManager(
    private val context: WeakReference<Context>
) : ModelAssetManager {
    override fun getAsset(info: ModelInfo): ModelBuilder? {
        var builder: ModelBuilder? = null

        try {
            builder =
                context.get()?.let { context ->
                    return ModelRenderable.builder()
                        .setSource(
                            context,
                            Uri.parse(info.filename)
                        )
                        .setIsFilamentGltf(true)
                        .setAsyncLoadEnabled(true)
                }

        } catch (e: Exception) {
            Log.e(ARTAG, "DEBUGTEST getAsset: ", e)
        }

        Log.d(ARTAG, "getAsset: , 3")
        return builder
    }
}