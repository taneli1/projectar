package com.example.projectar.data

import android.util.Log
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.product.ProductData

private const val TAG = "FakeProductList"

/**
 * Products listed in the application
 */
object FakeProductList {

    private val fakeImageInfo = ImageInfo(
        0,
        0,
        "none",
        "none"
    )

    private val fakeModelInfo = ModelInfo(
        0,
        0,
        "none"
    )

    private fun productData(number: Int) = ProductData(
        0,
        "Product $number",
        (Math.random() * 500).toFloat(),
        "Description for product $number"
    )

    val data: List<Product> = List(20) {
        return@List Product(
            productData(it),
            fakeImageInfo,
            fakeModelInfo
        )
    }
}