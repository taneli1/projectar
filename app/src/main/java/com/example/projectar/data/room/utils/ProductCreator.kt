package com.example.projectar.data.room.utils

import com.example.projectar.data.FakeProductList
import com.example.projectar.data.room.db.ApplicationDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Object to fill the database for development purposes
 */
object ProductCreator {

    @DelicateCoroutinesApi
    fun createProducts(database: ApplicationDatabase) {
        GlobalScope.launch {
            FakeProductList.data.forEach {
                database.productDao()
                    .insertProduct(it)
            }
        }
    }
}