package com.example.projectar.data.room.queryfilters

import com.example.projectar.data.appdata.tags.ProductTags
import com.example.projectar.data.utils.TagUtils

/**
 * Filter used by room to match tags in queries with search param.
 */
interface TagFilter {
    /** Get entities from database matching this string*/
    val searchTerm: String;

    /** Get entities from database which have the Tags listed */
    val tags: List<ProductTags>
}
