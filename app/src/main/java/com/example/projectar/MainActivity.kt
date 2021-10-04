package com.example.projectar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectar.databinding.ActivityMainBinding
import com.example.projectar.ui.utils.ArViewUiProvider
import com.example.projectar.ui.utils.ArViewUtils

class MainActivity : AppCompatActivity(), ArViewUiProvider {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showInterface() {
        ArViewUtils.applyArHud(
            composeView = binding.composeView
        )
    }

    override fun hideInterface() {
        ArViewUtils.releaseArHud(
            composeView = binding.composeView
        )
    }
}

