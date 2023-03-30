package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@HiltAndroidTest
class RegisterScreenTest {
    var clicks = 0

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()

        clicks = 0
        composeRule.setContent {
            RegisterScreen(
                onRegistered = { clicks++ }
            )
        }
    }

    @Test
    fun testScreenIsDisplayed() {
        composeRule.onNodeWithTag("register-screen__main-container").assertIsDisplayed()
        composeRule.onNodeWithText("Register screen").assertIsDisplayed()
    }

    @Test
    fun testRegisteredButtonWorks() {
        composeRule.onNodeWithTag("register-screen__registered-button").assertIsDisplayed()
        composeRule.onNodeWithTag("register-screen__registered-button").assertHasClickAction()
        assertEquals(0, clicks)
        composeRule.onNodeWithTag("register-screen__registered-button").performClick()
        assertEquals(1, clicks)
        composeRule.onNodeWithTag("register-screen__registered-button").performClick()
        assertEquals(2, clicks)
    }
}