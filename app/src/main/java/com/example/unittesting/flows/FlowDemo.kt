package com.example.unittesting.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

// For flows testing in android, I am changing a function's return type of room db to flow instead of live data.
class FlowDemo {
    fun getFlow() =
        flow<Int> {
            emit(1)
            delay(2000)
            emit(2)
            delay(1000)
        }
}
