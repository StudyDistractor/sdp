package com.github.studydistractor.sdp.distraction

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.ui.DistractionScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DistractionScreenTest {
    val name = "test"
    val description = "desc"
    val viewmodel = DistractionViewModel()

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()
        val distraction = Distraction(name, description)
        viewmodel.addDistraction(distraction)
        composeTestRule.setContent {
            DistractionScreen(viewmodel)
        }
    }

    @Test
    fun distractionIsDisplayedCorrectly() {
        composeTestRule.onNodeWithTag("name").assertExists()
        composeTestRule.onNodeWithTag("name").assert(hasText(name))
        composeTestRule.onNodeWithTag("description").assertExists()
        composeTestRule.onNodeWithTag("description").assert(hasText(description))
    }
}