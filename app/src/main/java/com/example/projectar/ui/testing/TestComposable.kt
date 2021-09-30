package com.example.projectar.ui.testing

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.di.Injector
import com.example.projectar.ui.viewmodel.ProductViewModel

object TestComposable {

    @Composable
    fun TestScreen(
        database: ApplicationDatabase,
    ) {

        val viewModel: ProductViewModel = viewModel(
            factory = Injector.provideProductViewModelFactory(database)
        )
        val products: List<Product> by viewModel.filteredProducts.observeAsState(listOf())


        TestList(
            data = products,
        ) { viewModel.createProducts(database) }
    }

    @Composable
    fun TestList(
        data: List<Product>,
        createProducts: () -> Unit
    ) {
        LazyColumn {
            items(data) { prod ->
                ProductTestCard(prod)
            }
        }
        Button(onClick = createProducts) {
            Text(text = "Add 20 products to database")
        }

    }

    @Composable
    fun ProductTestCard(product: Product) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            // We toggle the isExpanded variable when we click on this Column
            Column() {
                Text(
                    text = product.data.title,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )

                Text(
                    text = product.data.description ?: "No desc",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.data.price.toString(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )

            }
        }
    }
}