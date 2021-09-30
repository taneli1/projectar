package com.example.projectar.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    @TypeConverter
    fun stringToMap(value: String): Map<Long, Int> {
        val mapType = object : TypeToken<Map<Long, Int>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun mapToString(value: Map<Long, Int>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}