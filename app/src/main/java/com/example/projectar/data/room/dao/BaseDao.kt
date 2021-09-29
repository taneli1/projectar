package com.example.projectar.data.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

/**
 * Provides basic methods for parameter T
 */
@Dao
interface BaseDao<T> {
    @Insert(onConflict = REPLACE)
    fun insert(obj: T): Long

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}