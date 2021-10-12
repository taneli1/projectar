package com.example.projectar.data.utils

import android.content.Context
import com.example.projectar.data.appdata.tags.ProductTag
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
            ProductTag.values().size.toLong()
        )
    }

    fun getAllTags(): List<ProductTag> {
        return ProductTag.values()
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
        for (tag in ProductTag.values()) {
            if (tag.id() == tagId) {
                return context.getString(tag.resourceStringId())
            }
        }

        // If this value is returned, database prob has an error in it with tags.
        return "Tag error in database"
    }
}
