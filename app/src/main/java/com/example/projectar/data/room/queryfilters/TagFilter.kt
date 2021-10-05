package com.example.projectar.data.room.queryfilters

import com.example.projectar.data.appdata.tags.ProductTag

enum class SortBy {
    DEFAULT,
    ALPHABETICAL_ASC,
    ALPHABETICAL_DESC,
    PRICE_ASC,
    PRICE_DESC,
}


/**
 * Filter used by room to match tags in queries with search param.
 */
interface TagFilter {
    /** Get entities from database matching this string*/
    val searchTerm: String;

    /** Get entities from database which have the Tags listed */
    val tags: List<ProductTag>

    val sortBy: SortBy
}
