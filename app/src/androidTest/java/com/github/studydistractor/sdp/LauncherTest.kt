package com.github.studydistractor.sdp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.register.RegisterActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test

@HiltAndroidTest
class LauncherTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<Launcher>()

    @Before
    fun setup() {
        rule.inject()
    }
    @Test
    fun testDistract(){
        composeTestRule.onNodeWithTag("distract").performClick()
    }
}