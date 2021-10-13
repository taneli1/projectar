package com.example.projectar.ui.utils

import androidx.compose.ui.graphics.Color
import com.example.projectar.ui.theme.Beige
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.Orange

object ColorUtils {

    fun getBackGroundColor(number: Float): Color {
        return when {
            number < 100 -> DarkGrey
            number < 200 -> Orange
            else -> Beige
        }
    }
}