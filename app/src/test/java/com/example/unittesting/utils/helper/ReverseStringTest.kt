package com.example.unittesting.utils.helper

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class ReverseStringTest(private val input: String, private val output: String) {
    @Test
    fun test() {
        val helper = Helper()
        val result = helper.reverseString(input)
        assertEquals(output, result)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index} : {0} return {1}")
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf("abc", "cba"),
                arrayOf("", ""),
                arrayOf("ccc", "ccc"),
                arrayOf("aba", "aba"),
            )
        }
    }
}
