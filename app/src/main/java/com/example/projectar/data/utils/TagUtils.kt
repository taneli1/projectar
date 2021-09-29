package com.example.projectar.data.utils

import android.content.Context
import com.example.projectar.data.appdata.tags.ProductTags
import kotlin.random.Random

/**
 * Tools to help Tag usage
 */
object TagUtils {

    /**
     * Get a random tag value to add for a product
     */
    fun getRandomTagValue(): Long {
        return Random.nextLong(
            ProductTags.values().size.toLong()
        )
    }

    fun getAllTags(): List<ProductTags> {
        return ProductTags.values()
            .toList()
    }

    /**
     * Get a string corresponding to provided tag id.
     */
    fun getStringForTag(
        tagId: Long,
        context: Context
    ): String {
        // Check all the defined tags, return a string if matched
        for (tag in ProductTags.values()) {
            if (tag.id() == tagId) {
                return context.getString(tag.resourceString())
            }
        }

        // If this value is returned, database has an error in it.
        return "Tag error in database"
    }

}
