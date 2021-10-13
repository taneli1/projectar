package com.example.projectar.data.room.entity.file

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.projectar.data.room.entity.product.ProductData

/**
 * 3D-Model info for a product
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = ProductData::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = ["id"],
        childColumns = ["refId"]
    )],
    indices = [
        Index("refId"),
    ]
)
data class ModelInfo(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    override val refId: Long,
    override val filename: String
) : FileInfo
