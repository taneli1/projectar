package com.example.projectar.di

import android.content.Context
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.datahandlers.assets.ResourceImageManager
import com.example.projectar.data.datahandlers.assets.ResourceModelManager
import com.example.projectar.data.datahandlers.cart.CartImpl
import com.example.projectar.data.datahandlers.order.builder.OrderBuilderImpl
import com.example.projectar.data.datahandlers.order.handler.LocalOrderHandler
import com.example.projectar.data.datahandlers.product.ProductManagerImpl
import com.example.projectar.data.repository.ProductRepositoryImpl
import com.example.projectar.data.repository.interfaces.ProductRepository
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.ui.functional.ar.ArViewManager
import com.example.projectar.ui.functional.ar.ArViewManagerImpl
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.functional.viewmodel.ProductViewModelImpl
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import java.lang.ref.WeakReference

/**
 * Inject dependencies for the objects of the application
 */
object Injector {
    private const val FAKE_USER_ID = 1244L

    fun provideArViewManager(
        viewModel: ProductViewModel,
        arFragment: ArFragment,
        builder: (modelBuilder: ModelBuilder, function: (model: ModelRenderable) -> Unit) -> Unit
    ): ArViewManager {
        return ArViewManagerImpl(viewModel, WeakReference(arFragment), builder)
    }

    /**
     * Provides a ViewModelFactory for a ProductViewModel.
     * !Currently creates a new instance for the repo for each call!
     */
    fun provideProductViewModelFactory(
        database: ApplicationDatabase,
        context: Context
    ): ProductViewModelImpl.ProductViewModelFactory {
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
    ): ProductViewModelImpl.ProductViewModelFactory {
        return ProductViewModelImpl.ProductViewModelFactory(
            ProductManagerImpl(
                repo,
                ResourceImageManager(WeakReference(context)),
                ResourceModelManager(WeakReference(context)),
                CartImpl(),
                LocalOrderHandler(repo),
                OrderBuilderImpl(FAKE_USER_ID)
            )
        )
    }
}