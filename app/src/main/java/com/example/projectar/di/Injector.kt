package com.example.projectar.di

import android.content.Context
import com.example.projectar.data.managers.assets.ImageAssetManager
import com.example.projectar.data.managers.assets.ResourceImageManager
import com.example.projectar.data.managers.assets.ResourceModelManager
import com.example.projectar.data.managers.product.ProductManagerImpl
import com.example.projectar.data.repository.ProductRepositoryImpl
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.ui.viewmodel.ProductViewModel
import java.lang.ref.WeakReference

/**
 * Inject dependencies for the objects of the application
 */
object Injector {

    /**
     * Provides a ViewModelFactory for a ProductViewModel.
     * !Currently creates a new instance for the repo for each call!
     */
    fun provideProductViewModelFactory(
        database: ApplicationDatabase,
        context: Context
    ): ProductViewModel.ProductViewModelFactory {
        val repo = productRepository(database)
        return productViewModelFactory(repo, context)
    }

    private fun productRepository(
        database: ApplicationDatabase
    ): ProductRepository {
        return ProductRepositoryImpl(database)
    }

    private fun productViewModelFactory(
        repo: ProductRepository, context: Context
    ): ProductViewModel.ProductViewModelFactory {
        return ProductViewModel.ProductViewModelFactory(
            ProductManagerImpl(
                repo,
                ResourceImageManager(WeakReference(context)),
                ResourceModelManager(WeakReference(context))
            )
        )
    }
}