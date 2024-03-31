package com.example.unittesting.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// TestWatcher class allows us to write our own custom rules.
// In case of coroutine testing it is required to set/reset request for main dispatcher. so we could write a rule for it here so that we don't need to write same code again and again.
class MainCoroutineRule : TestWatcher() {
    // It creates a thread which tries to execute all the coroutines on that thread only. Coz in testing environment we try to have only one thread, o/w the testcase won't be reliable since it will be having dependency on other threads.
    // So, we can have multiple coroutines, but they all will be executed on a single thread.
    val testDispatcher = StandardTestDispatcher()

    // represents @Before
    override fun starting(description: Description) {
        super.starting(description)
        // In jvm testing environment, we can't access android's main dispatcher. That's why we are instructing the compiler that whenever you receive a request for main dispatcher then, replace it with testDispatcher.
        Dispatchers.setMain(testDispatcher)
    }

    // represents @After
    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
