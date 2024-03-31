package com.example.unittesting.utils.helper

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class ValidatePasswordTest(private val input: String, private val output: String) {
    @Test
    fun test() {
        val helper = Helper()
        val result = helper.validatePassword(input)
        assertEquals(output, result)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index} : {0} returns {1}")
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf("", "wrong"),
                arrayOf("abc1235", "abc1235"),
                arrayOf(" ", "wrong"),
                arrayOf("213", "wrong"),
            )
        }
    }
}
