package com.example.unittesting.utils

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuotesDao
}
