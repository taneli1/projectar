package com.example.projectar.data.managers.assets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.material.contentColorFor
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.drawable.toBitmap
import com.example.projectar.R
import com.example.projectar.data.room.entity.file.FileInfo
import com.example.projectar.data.room.entity.file.ImageInfo
import java.lang.Error
import java.lang.Exception
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
                "getAsset: Error getting image from resources $e"
            )
        }

        // Returns an empty bitmap if resource file could not be gotten
        return Bitmap.createBitmap(
            100,
            100,
            Bitmap.Config.ARGB_8888
        )
    }
}
