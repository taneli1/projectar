package com.example.projectar.data.room.db

import android.content.Context
import androidx.room.*
import com.example.projectar.data.room.dao.ProductDao
import com.example.projectar.data.room.dao.TagDao
import com.example.projectar.data.room.entity.file.ImageInfo
import com.example.projectar.data.room.entity.file.ModelInfo
import com.example.projectar.data.room.entity.product.ProductData
import com.example.projectar.data.room.entity.tag.Tag
import com.example.projectar.data.room.entity.tag.TagLink

@Database(
    entities = [(ProductData::class), (ImageInfo::class), (ModelInfo::class), (Tag::class), (TagLink::class)],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun tagDao(): TagDao

    companion object {
        private var i: ApplicationDatabase? = null

        fun get(context: Context): ApplicationDatabase {
            return i ?: synchronized(this) {
                i ?: Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "database.db"
                )
                    .build()
                    .also {
                        i = it
                    }
            }

        }
    }
}

