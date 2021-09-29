package com.example.projectar.data.managers.assets

import android.graphics.Bitmap
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.file.FileInfo

/**
 * Objects that can provide data for assets defined
 * by implementations of FileInfo.
 * @see FileInfo
 */
interface AssetManager {
    fun getImage(imageInfo: ImageInfo): Bitmap
    fun get3dModel(modelInfo: ModelInfo)
}