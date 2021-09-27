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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectar.data.FakeProductList
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.entity.product.Product
import com.example.projectar.di.Injector
import com.example.projectar.ui.testing.TestComposable
import com.example.projectar.ui.theme.ProjectarTheme
import com.example.projectar.ui.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy { ApplicationDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectarTheme {
                TestComposable.TestScreen(db)
            }
        }
    }
}
