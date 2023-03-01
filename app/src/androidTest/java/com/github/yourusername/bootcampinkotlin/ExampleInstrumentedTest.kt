package com.github.yourusername.bootcampinkotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    //Perform end-to-end test
    @Test
    fun enterBobChangeActivityThenGreetsBob() {
        composeTestRule.onNodeWithTag("textFieldName").performTextInput("Bob")
        composeTestRule.onNodeWithTag("textFieldName").assert(hasText("Bob"))
        composeTestRule.onNodeWithText("Greets").performClick()
        composeTestRule.onNodeWithTag("greetingText").assert(hasText("Hi Bob!"))
    }
}