package com.github.studydistractor.sdp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StudyDistractorScreenTest {
    private var successfullyRegistered = false

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        successfullyRegistered = false
        composeTestRule.setContent {
            StudyDistractorApp()
        }
    }
    @Test
    fun assertShow(){
        composeTestRule.onNodeWithTag("app-bar-top").assertIsDisplayed()
        composeTestRule.onNodeWithTag("app-bar-bottom").assertIsDisplayed()
    }
}