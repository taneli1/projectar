package com.example.projectar.data.productdata.tags

import android.content.Context
import androidx.compose.material.contentColorFor
import kotlin.random.Random

/**
 * Tools to help tag usage
 */
object TagTools {

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

        // This only returns if there is an error in the database values for tags
        return "Tag error in database"
    }

}
