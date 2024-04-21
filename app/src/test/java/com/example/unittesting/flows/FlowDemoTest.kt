package com.example.unittesting.flows

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class FlowDemoTest {
    @Test
    fun getFlow() =
        runTest {
            val sut = FlowDemo()
            val resultViaFirst = sut.getFlow().first()
            val resultViaToList = sut.getFlow().toList()
            // we can use first(), take(), toList() to assert the TC. These are terminal operators which will start the flow
            Assert.assertEquals(1, resultViaFirst)
            Assert.assertEquals(listOf(1, 2), resultViaToList)
        }
}
