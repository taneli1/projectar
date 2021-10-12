package com.example.projectar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.projectar.data.datahandlers.assets.ARTAG
import com.example.projectar.data.datahandlers.assets.ModelBuilder
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.data.room.utils.ProductCreator
import com.example.projectar.databinding.ActivityMainBinding
import com.example.projectar.di.Injector
import com.example.projectar.ui.fragment.ArViewFragment
import com.example.projectar.ui.fragment.ComposeFragment
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.functional.viewmodel.ProductViewModelImpl
import com.example.projectar.ui.screens.CHANNEL_ID
import com.example.projectar.ui.utils.ArViewUiProvider
import com.example.projectar.ui.utils.ArViewUtils
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), ArViewUiProvider {
    private val db by lazy { ApplicationDatabase.get(applicationContext) }
    private lateinit var viewModel: ProductViewModel
    private lateinit var binding: ActivityMainBinding


    object NotificationBuilder {
        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "text",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Content desc"
                }

                // Register the channel with the system
                val notificationManager: NotificationManager =
                    context.getSystemService(NOTIFICATION_SERVICE) as
                            NotificationManager

                notificationManager.createNotificationChannel(channel)
            }
        }

        fun sendTestNotification(context:Context) {
            // when you want to send the notification
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.goat)
                .setContentTitle("Order status")
                .setContentText("Your order has been received")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            NotificationManagerCompat.from(context).notify(1, notification)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationBuilder.createNotificationChannel(this)

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
                Log.d(ARTAG, "buildModel: ${throwable.stackTraceToString()}")
                null
            }
        }
    }

}

