@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.unittesting.utils.helper

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

// The package structure of this class is exactly same as Helper class.
// This is to avoid any accessibility issues of private-public members
class HelperTest {
    private lateinit var helper: Helper

    // @Before defines a function which needs to be executed before executing the testcases
    // like initialization of common objects.
    // Should not define multiple @Before fun as order of execution is not guaranteed
    @Before
    fun setUp() {
        helper = Helper()
    }

    // @After defines a function which needs to be executed after the executing the testcases
    // like freeing up the resources
    @After
    fun tearDown() {
    }

    // @Test defines each test case that we want to define
    @Test
    fun isPalindrome_hello_isFalse() {
        // Arrange
        val helper = Helper()
        // Act
        val result = helper.isPalindrome("hello")
        // Assert
        assertEquals(false, result)
    }

    @Test
    fun isPalindrome_level_isTrue() {
        val helper = Helper()
        val result = helper.isPalindrome("level")
        assertEquals(true, result)
    }
    // Here, the code of each testcase is being repeated for different inputs,
    // to tackle this we use parameterized testcases
}
