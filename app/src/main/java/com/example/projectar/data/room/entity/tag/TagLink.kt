package com.example.projectar.data.room.entity.tag

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Entity which links tags to other entities.
 */
@Entity(
    primaryKeys = ["linkId", "tagId"],
    foreignKeys = [ForeignKey(
        entity = Tag::class,
        childColumns = ["tagId"],
        parentColumns = ["tagId"]
    )],
    indices = [
        Index("tagId"),
    ]
)
data class TagLink(
    val linkId: Long,
    val tagId: Long
)
