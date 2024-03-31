package com.example.unittesting.utils.quotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.unittesting.utils.getOrAwaitValue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuotesDaoTest {
    lateinit var quoteDatabase: QuotesDatabase
    lateinit var quotesDao: QuotesDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // in memory database means the database instance will be created in the memory,
        // it will stay in the memory as long as the app is open, after that it is destroyed.
        // Why so, coz testcases should run in isolation, other testcases should not affect it.
        // so. we will require new instance of db for each testcase. That's why writing it in @Before
        quoteDatabase =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                QuotesDatabase::class.java,
            ).allowMainThreadQueries().build()
        // in main thread coz, this is a testing environment and we don't want multiple threads to come into play.
        quotesDao = quoteDatabase.quoteDao()
    }

    @After
    fun tearDown() {
        quoteDatabase.close()
    }

    @Test
    fun insertQuote_expectedSingleQuote() {
        runBlocking {
            val quote =
                Quote(id = 1, "This is a test quote", "Cheezy Code")
            quotesDao.insertQuote(quote)
            val result = quotesDao.getQuotes().getOrAwaitValue()
            assertEquals(1, result.size)
            assertEquals("This is a test quote", result[0].quote)
        }
    }

    @Test
    fun deleteDb_expectedEmptyDb() =
        runBlocking {
            val quote =
                Quote(id = 1, "This is a test quote", "Cheezy Code")
            quotesDao.insertQuote(quote)
            quotesDao.delete()
            val result = quotesDao.getQuotes().getOrAwaitValue()
            assertEquals(0, result.size)
        }
}
