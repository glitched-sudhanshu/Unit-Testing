package com.example.unittesting.utils.quotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuotesDaoFlowTest {
    lateinit var quoteDatabase: QuotesDatabase
    lateinit var quotesDao: QuotesDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        quoteDatabase =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                QuotesDatabase::class.java,
            ).allowMainThreadQueries().build()
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
            val resultViaFirst = quotesDao.getQuotesViaFlow().first()
// In case of flows in room db, it continuously gives us updates if anything is changed, due to which it runs infinitely. Since it runs infinitely, toList() method will never be completed. That is why, in case of infinite flows, we test them by asserting its first 1-2 elements.
//            val resultViaList = quotesDao.getQuotesViaFlow().toList()
            assertEquals(1, resultViaFirst.size)
            assertEquals("This is a test quote", resultViaFirst[0].quote)
        }
    }

    @Test
    fun insertQuote_expectedSingleQuoteViaTurbine() {
        runBlocking {
            val quote =
                Quote(id = 1, "This is a test quote", "Cheezy Code")
            quotesDao.insertQuote(quote)
            quotesDao.getQuotesViaFlow().test {
                // calling await item would give us the first value in the flow.
                val quotesList = awaitItem()
                assertEquals(1, quotesList.size)
                assertEquals("This is a test quote", quotesList[0].quote)
            }
        }
    }

    @Test
    fun insertQuote_expectedDoubleQuoteViaTurbine() {
        runBlocking {
            val quote =
                Quote(id = 1, "This is a test quote", "Cheezy Code")
            val quote2 =
                Quote(id = 2, "This is a test quote 2", "Cheezy Code")
            quotesDao.insertQuote(quote)
            quotesDao.insertQuote(quote2)
            quotesDao.getQuotesViaFlow().test {
                // here we inserted to quotes at once, that's why there are 2 items in the flow from start.
                val quotesList = awaitItem()
                assertEquals(2, quotesList.size)
                assertEquals("This is a test quote 2", quotesList[1].quote)
            }
        }
    }

    @Test
    fun insertQuote_expectedDoubleQuoteAfterDelay() {
        runBlocking {
            val quote =
                Quote(id = 1, "This is a test quote", "Cheezy Code")
            val quote2 =
                Quote(id = 2, "This is a test quote 2", "Cheezy Code")
            quotesDao.insertQuote(quote)
            launch {
                delay(500L)
                quotesDao.insertQuote(quote2)
            }
            quotesDao.getQuotesViaFlow().test {
                // here we added the other quote after some delay that's why at first the value in flow is 1.
                val quotesList = awaitItem()
                assertEquals(1, quotesList.size)
                // After delay when quote is inserted the flow's second value becomes 2, that's why calling awaitItem second times returns the 2nd value of flow.
                val quotesList2 = awaitItem()
                assertEquals(2, quotesList2.size)
            }
        }
    }
}
