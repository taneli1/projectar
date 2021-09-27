package com.example.projectar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.projectar.data.FakeProductList
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.data.room.utils.ProductCreator
import com.example.projectar.ui.theme.ProjectarTheme

class MainActivity : ComponentActivity() {
    private val db by lazy { ApplicationDatabase.get(this) }
    private lateinit var livedata: LiveData<List<Product>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        livedata = db.productDao()
            .getAllProducts()

        livedata.observe(this) {
            setContent {
                ProjectarTheme {
                    TestList(products = it)
                }
            }
        }
    }

}

@Composable
fun TestList(products: List<Product>) {
    LazyColumn {
        items(products) { message ->
            ProductTestCard(message)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectarTheme {
        ProductTestCard(product = FakeProductList.data[0])
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