package com.github.studydistractor.sdp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.ui.core.compose
import com.github.studydistractor.sdp.ui.RegisterScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainNavigationTest {
    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()

        composeRule.setContent {
            StudyDistractorApp()
        }
    }

    @Test
    fun testNavBarIsDisplayed() {
        composeRule.onNodeWithTag("app-bar-bottom").assertIsDisplayed()
    }

    @Test
    fun testNavigationWorks() {
        composeRule.onNodeWithTag("login-screen__main-container").assertIsDisplayed()

        composeRule.onNodeWithTag("app-bar-bottom__home-button").performClick()
        composeRule.onNodeWithTag("login-screen__main-container").assertIsDisplayed()
        composeRule.onNodeWithTag("maps-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("distraction-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("create-distraction-screen__main-container").assertDoesNotExist()

        composeRule.onNodeWithTag("app-bar-bottom__map-button").performClick()
        composeRule.onNodeWithTag("login-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("maps-screen__main-container").assertIsDisplayed()
        composeRule.onNodeWithTag("distraction-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("create-distraction-screen__main-container").assertDoesNotExist()

        composeRule.onNodeWithTag("app-bar-bottom__list-button").performClick()
        composeRule.onNodeWithTag("login-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("maps-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("distraction-screen__main-container").assertIsDisplayed()
        composeRule.onNodeWithTag("create-distraction-screen__main-container").assertDoesNotExist()

        composeRule.onNodeWithTag("app-bar-bottom__magic-button").performClick()
        composeRule.onNodeWithTag("login-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("maps-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("distraction-screen__main-container").assertDoesNotExist()
        composeRule.onNodeWithTag("create-distraction-screen__main-container").assertIsDisplayed()
    }
}