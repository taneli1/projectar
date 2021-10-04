package com.example.projectar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectar.data.room.db.ApplicationDatabase
import com.example.projectar.databinding.ActivityMainBinding
import com.example.projectar.di.Injector
import com.example.projectar.ui.functional.viewmodel.ProductViewModel
import com.example.projectar.ui.functional.viewmodel.ProductViewModelImpl
import com.example.projectar.ui.utils.ArViewUiProvider
import com.example.projectar.ui.utils.ArViewUtils

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

    override fun showInterface() {
        ArViewUtils.attachArHud(binding.composeView, viewModel)
    }

    override fun hideInterface() {
        ArViewUtils.releaseArHud(binding.composeView)
    }
}

