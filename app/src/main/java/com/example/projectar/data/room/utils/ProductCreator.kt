package com.example.projectar.data.room.utils

import com.example.projectar.data.productdata.products.FakeProductList
import com.example.projectar.data.productdata.tags.ProductTags
import com.example.projectar.data.productdata.tags.TagTools
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.entity.tag.TagLink
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Object to fill the database for development purposes
 */
object ProductCreator {

    @DelicateCoroutinesApi
    fun createProducts(database: ApplicationDatabase) {
        GlobalScope.launch {

            // Update tags
            for (tag in ProductTags.values()) {
                database.tagDao()
                    .insert(Tag(tag.id()))
            }


            FakeProductList.data.forEach {
                val id = database.productDao()
                    .insertProduct(it)
                val randomTagId = TagTools.getRandomTagValue()

                val tagLink = TagLink(
                    id = id,
                    tagId = randomTagId
                )

                database.tagDao()
                    .setTagForProduct(tagLink)
            }
        }
    }
}