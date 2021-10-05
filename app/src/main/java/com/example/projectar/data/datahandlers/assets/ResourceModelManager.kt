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

const val ARTAG = "AR TAG FOR DEV"

/**
 * Get 3dModels from resources
 */
class ResourceModelManager(
    private val context: WeakReference<Context>
) : ModelAssetManager {
    private val job = Job()
    private val scope = CoroutineScope(job)


    companion object {
        private const val TAG = "ResourceModelManager"
        const val RESOURCES_RAW = "raw"
    }

    override suspend fun getAsset(info: ModelInfo): Model? {
        var builder: Model? = null

        try {
            builder = scope.async {
                context.get()?.let { context ->
                    return@async ModelRenderable.builder()
                        .setSource(context, Uri.parse(info.filename))
                        .setAsyncLoadEnabled(true)
                        .setIsFilamentGltf(true)

                }
            }.await()

        } catch (e: Exception) {
            Log.e(ARTAG, "DEBUGTEST getAsset: ", e)
        }

        Log.d(ARTAG, "getAsset: , 3")
        return builder
    }
}