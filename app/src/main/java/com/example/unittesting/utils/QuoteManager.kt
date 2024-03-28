package com.example.unittesting.utils

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val quote: String,
    val author: String,
)

class QuoteManager() {
    var quotes = emptyArray<Quote>()
    var currentQuote = 0

    fun getQuotesFromJson(
        context: Context,
        fileName: String,
    ) {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        quotes = gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getNextQuote(): Quote {
        if (currentQuote == quotes.size - 1) currentQuote = -1
        ++currentQuote
        return quotes[currentQuote]
    }

    fun getPreviousQuote(): Quote {
        if (currentQuote == 0) currentQuote = quotes.size
        return quotes[--currentQuote]
    }
}
