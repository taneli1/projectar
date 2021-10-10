package com.example.projectar.ui.utils

import java.util.*

object DateFormatter {

    fun getDateString(date: Date): String {
        // Custom formatting since Java.Util.Date decided to not comply
        val iterator = date.toString().split("\\s".toRegex()).iterator()

        var str = ""

        // Take 3 elements, contains weekday, month, date
        repeat(3) {
            str += "${iterator.next()} " // whitespace at the end
        }

        return str
    }
}