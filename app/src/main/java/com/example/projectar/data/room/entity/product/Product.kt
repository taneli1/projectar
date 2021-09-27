package com.example.projectar.data.room.entity.product

import android.graphics.ImageDecoder
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo

/**
 * Representation for products in the application
 */
@Entity()
class Product(
    @Embedded val data: ProductData,
    @Relation(
        parentColumn = "id",
        entityColumn = "refId"
    ) val image: ImageInfo,
    @Relation(
        parentColumn = "id",
        entityColumn = "refId"
    ) val model: ModelInfo
)




