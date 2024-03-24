@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.unittesting

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    // @Rule is an instance of a class whereas @Before is a function.
    // To share common code among different test cases, rules are used
    // we can also pass in intents if we want
    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testNextButton_expectedCorrectQuote() {
        // Arrange
        onView(withId(R.id.btnNext))
            .perform(click()) // Act
        onView(withId(R.id.btnNext)).perform(click())
        onView(withId(R.id.btnNext)).perform(click())
        onView(
            withId(R.id.quoteText),
        ).check(matches(withText("Success is not final, failure is not fatal: It is the courage to continue that counts."))) // Assert
    }

    @Test
    fun testShareButton_expectedIntentChooser() {
        Intents.init()
        val expected = allOf(hasAction(Intent.ACTION_SEND))
        onView(withId(R.id.floatingActionButton)).perform(click())
        intended(expected)
        Intents.release()
    }
}
