package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.github.studydistractor.sdp.login.FakeLoginAuth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CreateDistractionScreenTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()
        composeRule.setContent {
            CreateDistractionScreen()
        }
    }

    @Test
    fun testScreenIsShown() {
        composeRule.onNodeWithTag("create-distraction-screen__main-container").assertIsDisplayed()
        composeRule.onNodeWithText("Create distraction screen").assertIsDisplayed()
    }
}