package com.example.projectar.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.entity.tag.TagLink
import com.example.projectar.data.room.queryfilters.SortBy
import com.example.projectar.data.room.queryfilters.TagFilter


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

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) ORDER BY title ASC")
    abstract fun getAllProductsForTagsAlphabeticalOrderAsc(
        tags: List<Long>,
    ): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) ORDER BY title DESC")
    abstract fun getAllProductsForTagsAlphabeticalOrderDesc(
        tags: List<Long>,
    ): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) ORDER BY price ASC")
    abstract fun getAllProductsForTagsPriceAsc(
        tags: List<Long>,
    ): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) ORDER BY price DESC")
    abstract fun getAllProductsForTagsPriceDesc(
        tags: List<Long>,
    ): LiveData<List<Product>>

    fun getProductsFiltered(filter: TagFilter): LiveData<List<Product>> {
        val list = filter.tags.map { it.id() }
        return when (filter.sortBy) {
            SortBy.ALPHABETICAL_ASC -> getAllProductsForTagsAlphabeticalOrderAsc(list)
            SortBy.ALPHABETICAL_DESC -> getAllProductsForTagsAlphabeticalOrderDesc(list)
            SortBy.PRICE_ASC -> getAllProductsForTagsPriceAsc(list)
            SortBy.PRICE_DESC -> getAllProductsForTagsPriceDesc(list)
            else -> getAllProductsForTags(list)
        }

    }
}