package com.example.projectar.data.room.dao

import androidx.room.Dao
import androidx.room.Query


@Dao
abstract class NukeDao {

    fun nukeDb() {
        deleteImages()
        deleteModels()
        deleteProductData()
        deleteTagLink()
        deleteTags()
    }

    @Query("DELETE FROM ProductData")
    abstract fun deleteProductData()


    @Query("DELETE FROM TagLink")
    abstract fun deleteTagLink()

    @Query("DELETE FROM Tag")
    abstract fun deleteTags()

    @Query("DELETE FROM ImageInfo")
    abstract fun deleteImages()

    @Query("DELETE FROM ModelInfo")
    abstract fun deleteModels()
}