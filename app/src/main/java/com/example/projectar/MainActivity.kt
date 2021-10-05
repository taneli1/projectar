package com.example.projectar

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectar.data.datahandlers.assets.ARTAG
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.databinding.ActivityMainBinding
import com.example.projectar.di.Injector
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.functional.viewmodel.ProductViewModelImpl
import com.example.projectar.ui.utils.ArViewUiProvider
import com.example.projectar.ui.utils.ArViewUtils
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment

class MainActivity : AppCompatActivity(), ArViewUiProvider {
    private val db by lazy { ApplicationDatabase.get(applicationContext) }
    private lateinit var viewModel: ProductViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init ViewModel here, use in fragments to share Cart data etc..
        viewModel = ViewModelProvider(
            this,
            Injector.provideProductViewModelFactory(db, applicationContext)
        ).get(ProductViewModelImpl::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun setupInterface(arFragment: ArFragment) {
        ArViewUtils.attachArHud(
            binding.composeView,
            Injector.provideArViewManager(viewModel, arFragment, ::buildModelRenderable),
            db
        )
    }

    override fun hideInterface() {
        ArViewUtils.releaseArHud(binding.composeView)
    }


    /**
     * A method to build a ModelRenderable on UIThread
     * @param function to run when the model has been built
     */
    private fun buildModelRenderable(
        modelBuilder: ModelBuilder,
        function: (model: ModelRenderable) -> Unit
    ) {
        runOnUiThread {
            modelBuilder.build().thenAccept {
                function(it)
            }.exceptionally { throwable ->
                Log.d(ARTAG, "buildModel: ${throwable.stackTraceToString()}")
                null
            }
        }
    }
}

