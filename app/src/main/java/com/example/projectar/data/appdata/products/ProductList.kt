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
            60f,
            "table/1/table.jpg",
            "table/1/table.gltf"
        ),
        createProduct(
            "Wooden table",
            "Wooden table. Good.",
            50f,
            "table/2/table2.jpg",
            "table/2/table2.gltf"
        ),
        createProduct(
            "Antique table",
            "Antique table. Nice.",
            399.99f,
            "table/3/table3.jpg",
            "table/3/table3.gltf"
        ),
        createProduct(
            "Mini table",
            "Mini table for small purposes.",
            20f,
            "table/4/table4.jpg",
            "table/4/table4.gltf"
        ),
        createProduct(
            "Basic table",
            "Basic table for variety of usages. Good.",
            170f,
            "table/5/table5.jpg",
            "table/5/table5.gltf"
        ),

        )

    private val lamps = mutableListOf<Product>(
        createProduct(
            "Red lamp",
            "Nice lamp for tables.",
            10f,
            "lamp/1/lamp.jpg",
            "lamp/1/lamp.gltf"
        ),
        createProduct(
            "Black lamp",
            "Nice lamp for tables.",
            12.30f,
            "lamp/2/lamp2.jpg",
            "lamp/2/lamp2.gltf"
        ),
        createProduct(
            "Exotic lamp",
            "Good nice nice.",
            30f,
            "lamp/3/lamp3.jpg",
            "lamp/3/lamp3.gltf"
        ),
        createProduct(
            "Table lamp",
            "Nice lamp for tables.",
            9.98f,
            "lamp/4/lamp4.jpg",
            "lamp/4/lamp4.gltf"
        ),
    )

    val data: List<Product> = mutableListOf<Product>().apply {
        addAll(tables)
        addAll(lamps)
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