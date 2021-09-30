package com.example.projectar.di

import com.example.projectar.data.managers.product.ProductManagerImpl
import com.example.projectar.data.repository.ProductRepositoryImpl
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.ui.viewmodel.ProductViewModel

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
    ): ProductViewModel.ProductViewModelFactory {
        val repo = productRepository(database)
        return productViewModelFactory(repo)
    }

    private fun productRepository(
        database: ApplicationDatabase
    ): ProductRepository {
        return ProductRepositoryImpl(database)
    }

    private fun productViewModelFactory(
        repo: ProductRepository
    ): ProductViewModel.ProductViewModelFactory {
        return ProductViewModel.ProductViewModelFactory(ProductManagerImpl(repo))
    }
}