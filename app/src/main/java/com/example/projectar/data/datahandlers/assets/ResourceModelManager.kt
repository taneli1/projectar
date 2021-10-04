package com.example.projectar.data.datahandlers.assets

import android.content.Context
import com.example.projectar.data.room.entity.file.ModelInfo
import java.lang.ref.WeakReference

/**
 * Get 3dModels from resources
 */
class ResourceModelManager(
    private val context: WeakReference<Context>
) : ModelAssetManager {

    override fun getAsset(info: ModelInfo): Model {
        TODO("Not yet implemented")
    }
}