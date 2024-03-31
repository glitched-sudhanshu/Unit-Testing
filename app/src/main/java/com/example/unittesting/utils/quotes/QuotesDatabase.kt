package com.example.unittesting.utils.quotes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuotesDao
}
