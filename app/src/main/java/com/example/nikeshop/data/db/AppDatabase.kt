package com.example.nikeshop.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.data.source.local.ProductLocalDataSource

@Database(entities = [Product::class] , version = 1 , exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun productDao():ProductLocalDataSource
}