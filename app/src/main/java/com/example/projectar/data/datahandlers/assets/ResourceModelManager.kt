package com.example.projectar.data.datahandlers.assets

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.utils.cacheFileInfo
import com.google.ar.sceneform.rendering.ModelRenderable
import java.lang.ref.WeakReference

/**
 * Get 3dModels from assets
 */
class ResourceModelManager(
    private val context: WeakReference<Context>
) : ModelAssetManager {
    // To increase performance.
    private val cache: LinkedHashMap<Long, ModelBuilder> = linkedMapOf()


    companion object {
        private const val CACHE_MAX_SIZE = 100
    }

    override fun getAsset(info: ModelInfo): ModelBuilder? {
        return cache.getOrElse(info.id) {
            var builder: ModelBuilder? = null

            try {
                builder =
                    context.get()?.let { context ->
                        val temp = ModelRenderable.builder()
                            .setSource(
                                context,
                                Uri.parse(info.filename)
                            )
                            .setIsFilamentGltf(true)
                            .setAsyncLoadEnabled(true)
                        cache.cacheFileInfo(info, temp, CACHE_MAX_SIZE)
                        return temp
                    }
            } catch (e: Exception) {
                Log.e("DEBUG", "getAsset: ", e)
            }
            return builder
        }
    }
}