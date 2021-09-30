package com.example.projectar.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.projectar.data.room.queryfilters.TagFilter
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.entity.tag.TagLink

@Dao
abstract class TagDao : BaseDao<Tag> {
    @Query("SELECT * FROM tag")
    abstract fun getAllTags(): LiveData<List<Tag>>

    @Insert
    abstract fun setTagForProduct(link: TagLink)

    @Transaction
    @Query("SELECT * FROM tag AS tags INNER JOIN tagLink AS links ON tags.tagId = links.tagId WHERE links.id = :productId")
    abstract fun getAllTagsForProduct(productId: Long): LiveData<List<Tag>>

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags)")
    abstract fun getAllProductsForTags(tags: List<Long>): LiveData<List<Product>>

    fun getProductsFiltered(filter: TagFilter): LiveData<List<Product>> {
        return getAllProductsForTags(filter.tags.map { it.id() })
    }
}