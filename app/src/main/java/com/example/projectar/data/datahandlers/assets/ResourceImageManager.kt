package com.example.projectar.data.datahandlers.assets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.example.projectar.data.room.entity.file.ImageInfo
import java.io.InputStream
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

    override fun getAsset(info: ImageInfo): Bitmap {
        try {
            context.get()
                ?.let {
                    val stream: InputStream = context.get()?.assets?.open(info.filename)
                        ?: throw Error("FileError")
                    val d = Drawable.createFromStream(stream, null)
                    return d.toBitmap()
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
