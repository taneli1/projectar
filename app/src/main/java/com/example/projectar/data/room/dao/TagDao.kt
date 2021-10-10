package com.example.projectar.data.room.dao

import android.util.Log
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
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) AND title LIKE :query")
    abstract fun getAllProductsForTags(tags: List<Long>, query: String): LiveData<List<Product>>

    fun getProductsFiltered(filter: TagFilter): LiveData<List<Product>> {
        val list = filter.tags.map { it.id() }
        val q = if (filter.searchTerm.isBlank()) "%" else "%" + filter.searchTerm.toString().trim() + "%"

        Log.d("DATABASE", "getProductsFiltered: $q")
        return when (filter.sortBy) {
            SortBy.ALPHABETICAL_ASC -> sortAlphabeticalASC(list, q)
            SortBy.ALPHABETICAL_DESC -> sortAlphabeticalDESC(list, q)
            SortBy.PRICE_ASC -> sortPriceASC(list, q)
            SortBy.PRICE_DESC -> sortPriceDESC(list, q)
            else -> getAllProductsForTags(list, q)
        }
    }

    // Copy paste to get products with ordering queries for the above method
    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) AND title LIKE :query ORDER BY title ASC")
    abstract fun sortAlphabeticalASC(tags: List<Long>, query: String): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) AND title LIKE :query ORDER BY title DESC")
    abstract fun sortAlphabeticalDESC(tags: List<Long>, query: String): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) AND title LIKE :query ORDER BY price ASC")
    abstract fun sortPriceASC(tags: List<Long>, query: String): LiveData<List<Product>>

    @Transaction
    @Query("SELECT * FROM productdata AS products INNER JOIN tagLink AS links ON products.id = links.id WHERE links.tagId IN (:tags) AND title LIKE :query ORDER BY price DESC")
    abstract fun sortPriceDESC(tags: List<Long>, query: String): LiveData<List<Product>>
}