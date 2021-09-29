package com.example.projectar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
