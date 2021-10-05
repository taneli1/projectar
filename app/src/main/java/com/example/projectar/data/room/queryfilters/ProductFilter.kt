package com.example.projectar.data.room.queryfilters

import com.example.projectar.data.appdata.tags.ProductTag
import com.example.projectar.data.utils.TagUtils

/**
 * Default implementation of TagFilter with values returning all values in queries.
 */
data class ProductFilter(
    override val searchTerm: String = "",
    override val tags: List<ProductTag> = TagUtils.getAllTags()
) : TagFilter
