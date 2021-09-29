package com.example.projectar.data.room.entity.tag

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.product.ProductData

///**
// * All products listed under a single tag.
// */
data class ProductListForTag(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "id",
        associateBy = Junction(
            TagLink::class,
        )
    ) val products: List<ProductData>
)

/**
 * All tags listed for a product
 */
data class TagListForProduct(
    @Embedded val product: ProductData,
    @Relation(
        parentColumn = "id",
        entityColumn = "tagId",
        associateBy = Junction(TagLink::class)
    ) val tags: List<Tag>
)