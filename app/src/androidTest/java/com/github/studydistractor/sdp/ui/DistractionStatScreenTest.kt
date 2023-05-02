package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.distractionStat.DistractionStatServiceFirebase
import com.github.studydistractor.sdp.distractionStat.DistractionStatViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DistractionStatScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {

        val viewModel = DistractionStatViewModel(DistractionStatServiceFirebase())
        composeTestRule.setContent {
            DistractionStatScreen(viewModel)
        }
    }
    @Test
    fun testButtonLike(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__like").assertIsDisplayed()
    }
    @Test
    fun testButtonDislike(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike").assertIsDisplayed()
    }
    @Test
    fun testTagTextField(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-text-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-button").assertIsDisplayed()
    }
    @Test
    fun testFeedbackTextField(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-text-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-button").assertIsDisplayed()
    }
}