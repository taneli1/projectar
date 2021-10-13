package com.example.projectar.data.appdata.products

import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.entity.product.ProductData

/**
 * Products listed in the application
 */
object ProductList {

    private val tables = mutableListOf(
        createProduct(
            "White table",
            "White table with metal legs. Good.",
            50f,
            "table/1/table.jpg",
            "table/1/table.gltf"
        )
    )

    private val sofas = mutableListOf<Product>()

    val data: List<Product> = mutableListOf<Product>().apply {
        addAll(tables)
        addAll(sofas)
    }

    private fun createProduct(
        title: String,
        desc: String,
        price: Float,
        imagePath: String,
        modelPath: String
    ): Product {
        return Product(
            data = productData(title, desc, price),
            image = imageInfo(imagePath),
            model = modelInfo(modelPath)
        )
    }

    private fun imageInfo(path: String): ImageInfo {
        return ImageInfo(
            0,
            0,
            path,
            "none"
        )
    }

    private fun modelInfo(path: String): ModelInfo {
        return ModelInfo(
            0,
            0,
            path
        )
    }

    private fun productData(title: String, desc: String, price: Float): ProductData {
        return ProductData(
            0,
            title,
            price,
            desc
        )
    }

}