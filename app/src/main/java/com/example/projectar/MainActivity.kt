package com.example.projectar

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.databinding.ActivityMainBinding
import com.example.projectar.di.Injector
import com.example.projectar.ui.fragment.ArViewFragment
import com.example.projectar.ui.fragment.ComposeFragment
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        // Init ViewModel here, use in fragments to share Cart data etc..
        viewModel = ViewModelProvider(
            this,
            Injector.provideProductViewModelFactory(db, applicationContext)
        ).get(ProductViewModelImpl::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)

        /**
         * MainActivity layout has a FragmentContainerView, which
         * is linked to a NavGraph.
         *
         * The NavGraph contains two different fragments, ComposeFragment + ArViewFragment.
         * ComposeFragment contains all the screens for the application, except
         * for the AR-View, which requires its own Fragment.
         *
         * Compose probably should not be used in a project
         * when using a Fragment with AR-functionality in the way this application uses.
         * (As a nav destination in the bottom tab bar. The ArFragment can't be implemented
         * as a simple Composable function.)
         *
         * @see ArViewFragment
         * @see ComposeFragment
         */
        setContentView(binding.root)
    }


    @OptIn(ExperimentalAnimationApi::class)
    override fun setupInterface(arFragment: ArFragment) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        ArViewUtils.attachArHud(
            binding.composeView,
            Injector.provideArViewManager(viewModel, arFragment, ::buildModelRenderable),
            viewModel,
            navController
        )
    }

    override fun hideInterface() {
        ArViewUtils.releaseArHud(binding.composeView)
    }


    /**
     * A method to build a ModelRenderable on UIThread.
     * The ModelRenderable.Builder.build() requires UI-Thread.
     * This gets passed to the class managing the models in the AR-Fragment which calls this
     * whenever a model is added to the scene.
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
                Log.d("DEBUG", "buildModel: ${throwable.stackTraceToString()}")
                null
            }
        }
    }

}

