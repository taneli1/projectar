package com.example.projectar.data.repository.intrfc

import com.example.projectar.data.productdata.tags.ProductTags
import com.example.projectar.data.productdata.tags.TagTools

/**
 * Filter out products in searches
 */
interface ProductFilter {
    val searchTerm: String;
    val tags: List<ProductTags>
}

/**
 * Implementation of filter.
 */
data class Filter(
    override val searchTerm: String = "",
    override val tags: List<ProductTags> = TagTools.getAllTags()
) : ProductFilter