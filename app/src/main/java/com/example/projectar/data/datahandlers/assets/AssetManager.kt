package com.example.projectar.data.datahandlers.assets

import android.graphics.Bitmap
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.file.FileInfo

/**
 * Objects that can provide data for assets defined
 * by implementations of FileInfo.
 *
 * Implementations of AssetManagers can decide for themselves where to get the resources
 * from, as long as the correct form is supplied.
 * @see FileInfo
 */
interface AssetManager<T : FileInfo, U> {
    fun getAsset(info: T): U
}

/**
 * Typealiases for image and model Managers
 */
typealias ImageAssetManager = AssetManager<ImageInfo, Bitmap>;
typealias ModelAssetManager = AssetManager<ModelInfo, Model>;

/** Placeholder for the model type */
typealias Model = String