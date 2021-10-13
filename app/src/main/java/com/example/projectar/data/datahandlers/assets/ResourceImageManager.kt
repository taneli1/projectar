package com.example.projectar.data.datahandlers.assets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.utils.cacheFileInfo
import java.io.InputStream
import java.lang.ref.WeakReference


/**
 * Gets image files from project resources
 */
class ResourceImageManager(
    private val context: WeakReference<Context>
) : ImageAssetManager {
    // To increase performance.
    private val cache: LinkedHashMap<Long, Bitmap> = linkedMapOf()

    companion object {
        // Max cached images
        private const val MAX_CACHE_SIZE = 100
        private const val TAG = "ResourceImageManager"
    }

    override fun getAsset(info: ImageInfo): Bitmap {
        return cache.getOrElse(info.id) {
            try {
                context.get()
                    ?.let {
                        val stream: InputStream = context.get()?.assets?.open(info.filename)
                            ?: throw Error("FileError")
                        val d = Drawable.createFromStream(stream, null)
                        cache.cacheFileInfo(info, d.toBitmap(), MAX_CACHE_SIZE)
                        return cache[info.id]!!
                    }
            } catch (e: Exception) {
                Log.d(
                    TAG,
                    " getAsset: Error getting image from resources $e"
                )
            }

            // Returns an empty bitmap if a resource file could not be found
            Bitmap.createBitmap(
                100,
                100,
                Bitmap.Config.ARGB_8888
            )
        }
    }


}
