package com.example.unittesting.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Util(private val dispatcher: CoroutineDispatcher) {
    suspend fun getUserName(): String {
        delay(4000)
        return "r02_sudhanshu"
    }

    suspend fun getUserNameOnMain(): String {
        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
        }
        return "r02_sudhanshu on main"
    }

    suspend fun getAddress(): String {
        // Android documentation suggests that its a better practice to inject dispatcher in the constructor of the class itself. Coz in turn it will help to write testcases where dispatchers like IO are used so that we can pass StandardTestDispatcher()
//        withContext(Dispatchers.IO) {
        withContext(dispatcher) {
            delay(4000)
        }
        return "@gmail.com"
    }

    var globalAgr = false

    fun getAddressDetails() {
        CoroutineScope(dispatcher).launch {
            delay(5000)
            globalAgr = true
        }
    }
}
