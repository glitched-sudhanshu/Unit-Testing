@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.unittesting.utils

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.google.gson.JsonSyntaxException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class QuoteManagerTest {
    private lateinit var quoteManager: QuoteManager
    private lateinit var context: Context
    private val quotes =
        arrayOf(Quote("hello", "0"), Quote("bye bye", "1"))

    @Before
    fun setUp() {
        quoteManager = QuoteManager()
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test(expected = FileNotFoundException::class)
    fun getQuotesFromJson_emptyFile_fileNotFoundException() {
        quoteManager.getQuotesFromJson(context, "")
    }

    @Test(expected = JsonSyntaxException::class)
    fun getQuotesFromJson_malFile_JsonSyntaxException() {
        quoteManager.getQuotesFromJson(context, "mal_quotes.json")
    }

    @Test
    fun getNextQuote_onFirst_returnsSecond() {
        quoteManager.quotes = this.quotes
        quoteManager.currentQuote = 0
        val result = quoteManager.getNextQuote()
        Log.d("getNextQuote_onFirst_returnsSecond", "$result : ")
        assertEquals("1", result.author)
    }

    @Test
    fun getNextQuote_onSecond_returnsFirst() {
        quoteManager.quotes = this.quotes
        quoteManager.currentQuote = 1
        val result = quoteManager.getNextQuote()
        assertEquals("0", result.author)
    }

    @Test
    fun getPrevQuote_onFirst_returnsSecond() {
        quoteManager.quotes = this.quotes
        quoteManager.currentQuote = 0
        val result = quoteManager.getPreviousQuote()
        assertEquals("1", result.author)
    }
}
