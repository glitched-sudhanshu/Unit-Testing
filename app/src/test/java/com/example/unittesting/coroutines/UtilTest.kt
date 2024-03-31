@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.unittesting.coroutines

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class UtilTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private var sut = Util(mainCoroutineRule.testDispatcher)

    @Test
    fun testGetUserName_expected_r02sudhanshu() {
        // we can also use runBlocking, both are almost the same. The only difference is that runTest is much more optimized for testcases, as it avoids unnecessary computations like delay()
        runTest {
            val result = sut.getUserName()
            assertEquals("r02_sudhanshu", result)
        }
    }

    @Test
    fun testGetUserNameOnMain_expected_r02sudhanshu_on_main() {
        runTest {
            val result = sut.getUserNameOnMain()
            assertEquals("r02_sudhanshu on main", result)
        }
    }

    @Test
    fun testGetAddress_expected_gmail() {
        runTest {
            val result = sut.getAddress()
            assertEquals("@gmail.com", result)
        }
    }

    @Test
    fun testGetAddressDetails_expected_true() {
        runTest {
            sut.getAddressDetails()
            // It is the responsibilty of the developer whether he needs to execute the coroutine or not. By default, they are not executed.
            // This line will execute all the coroutines, and will hold the code until all of them are executed
            mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(true, sut.globalAgr)
        }
    }
}
