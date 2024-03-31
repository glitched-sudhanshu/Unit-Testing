package com.example.unittesting

import java.io.InputStreamReader

object FileReaderUtil {
    fun execute(filename: String): String {
        val inputStream = FileReaderUtil::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}
