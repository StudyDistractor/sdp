package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.ui.core.setContent
import com.github.studydistractor.sdp.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        rule.inject()
    }
    @Test
    fun testClickingEveryWhere(){
        composeTestRule.onNodeWithTag("welcome").performClick()
        composeTestRule.onNodeWithTag("navigation").performClick()
        composeTestRule.onNodeWithTag("home").performClick()
        composeTestRule.onNodeWithTag("map").performClick()
        composeTestRule.onNodeWithTag("surprise").performClick()
    }

}