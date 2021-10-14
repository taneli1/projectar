package com.example.projectar.ui.utils

import androidx.compose.ui.graphics.Color
import com.example.projectar.ui.theme.Beige
import com.example.projectar.ui.theme.DarkGrey
import com.example.projectar.ui.theme.GreyBlue
import com.example.projectar.ui.theme.Orange

object ColorUtils {

    fun getBackGroundColor(number: Float): Color {
        return when {
            number < 50 -> Beige
            number < 100 -> DarkGrey
            else -> GreyBlue
        }
    }
}