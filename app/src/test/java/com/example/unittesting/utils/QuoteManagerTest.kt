package com.example.unittesting.utils

import android.content.Context
import android.content.res.AssetManager
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

// Here we are trying to convert the corresponding instrumentation test to a JVM test via mocking. In testing, we try to write as much JVM testcases as possible.
class QuoteManagerTest {
    @Mock
    lateinit var context: Context

    @Mock
    lateinit var assetManager: AssetManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test() {
        val testStream = QuoteManagerTest::class.java.getResourceAsStream("/testing_quotes.json")
        doReturn(assetManager).`when`(context).assets
        Mockito.`when`(context.assets.open(anyString())).thenReturn(testStream)
        val sut = QuoteManager()
        sut.getQuotesFromJson(context, "")
        assertEquals("The only way to do great work is to love what you do.", sut.quotes[0].quote)
    }
}
