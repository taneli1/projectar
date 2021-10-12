package com.example.projectar.data.datahandlers.assets

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.example.projectar.data.room.entity.file.ImageInfo
import java.lang.ref.WeakReference

/**
 * Gets image files from project resources
 */
class ResourceImageManager(
    private val context: WeakReference<Context>
) : ImageAssetManager {

    companion object {
        private const val TAG = "ResourceImageManager"
        const val RESOURCES_DRAWABLE = "drawable"
    }

    override suspend fun getAsset(info: ImageInfo): Bitmap {
        try {
            context.get()
                ?.let {
                    val img = AppCompatResources.getDrawable(
                        it,
                        it.resources.getIdentifier(
                            info.filename,
                            RESOURCES_DRAWABLE,
                            it.packageName
                        )
                    ) ?: throw Error("Image was null")

                    return img.toBitmap()
                }
        } catch (e: Exception) {
            Log.d(
                TAG,
                " getAsset: Error getting image from resources $e"
            )
        }

        // Returns an empty bitmap if a resource file could not be found
        return Bitmap.createBitmap(
            100,
            100,
            Bitmap.Config.ARGB_8888
        )
    }
}
