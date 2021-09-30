package com.example.projectar.data.room.entity.tag

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Relation
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.product.ProductData

/**
 * Entity which links tags to other entities.
 */
@Entity(
    primaryKeys = ["id", "tagId"],
    foreignKeys = [ForeignKey(
        entity = Tag::class,
        childColumns = ["tagId"],
        parentColumns = ["tagId"]
    )]
)
data class TagLink(
    val id: Long,
    val tagId: Long
)
