package com.example.projectar.data.room.utils

import com.example.projectar.data.appdata.products.ProductList
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.entity.tag.TagLink

/**
 * Object to fill the database for development purposes
 */
object ProductCreator {
    fun createProducts(database: ApplicationDatabase) {
        database.nukeDao().nukeDb()

        // Update tags
        for (tag in ProductTag.values()) {
            database.tagDao()
                .insert(Tag(tag.id()))
        }

        // Save all the products + add the tag
        ProductList.data.forEach { pair ->
            val products = pair.second

            products.forEach {
                val id = database.productDao()
                    .insertProduct(it)
                val tagLink = TagLink(
                    linkId = id,
                    tagId = pair.first.id()
                )
                database.tagDao()
                    .setTagForProduct(tagLink)
            }
        }

    }
}