package com.example.projectar.data.room.utils

import com.example.projectar.data.appdata.products.FakeProductList
import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.utils.TagUtils
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.entity.tag.TagLink
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Object to fill the database for development purposes
 */
object ProductCreator {

    fun createProducts(database: ApplicationDatabase) {
        GlobalScope.launch {

            // Update tags
            for (tag in ProductTag.values()) {
                database.tagDao()
                    .insert(Tag(tag.id()))
            }


            FakeProductList.data.forEach {
                val id = database.productDao()
                    .insertProduct(it)
                val randomTagId = TagUtils.getRandomTagValue()

                val tagLink = TagLink(
                    id = id,
                    tagId = randomTagId
                )

                database.tagDao()
                    .setTagForProduct(tagLink)
            }
        }
    }

    fun nuke(database: ApplicationDatabase) {
        GlobalScope.launch { database.nukeDao().nukeDb() }
    }
}