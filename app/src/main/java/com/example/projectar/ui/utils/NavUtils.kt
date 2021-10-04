package com.example.projectar.ui.utils

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.projectar.R

object NavUtils {
    /** Navigate to destination, pops everything from backstack? */
    fun navigate(navController: NavController, route: String) {
        navController.navigate(route) {
            popUpTo("testBox") { inclusive = true }
        }
    }
}