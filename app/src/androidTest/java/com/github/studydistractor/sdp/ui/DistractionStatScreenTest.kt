package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.distractionStat.DistractionStatServiceFirebase
import com.github.studydistractor.sdp.distractionStat.DistractionStatViewModel
import com.github.studydistractor.sdp.fakeServices.DistractionStatServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DistractionStatScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {

        val viewModel = DistractionStatViewModel(DistractionStatServiceFake())
        composeTestRule.setContent {
            DistractionStatScreen(viewModel)
        }
    }
    @Test
    fun testButtonLike(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__like").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__like").assertHasClickAction()
    }
    @Test
    fun testButtonDislike(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike").assertHasClickAction()
    }

    @Test
    fun testLikeDislikeButtonsRow(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__like-dislike-buttons").assertIsDisplayed()
    }

    @Test
    fun testLikeDislikeRow(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__like-dislike").assertIsDisplayed()
        Thread.sleep(3000)
        composeTestRule.onNodeWithTag("distraction-stat-screen__like-count").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__like-count").assert(hasText("Likes : 0"))
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike-count").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike-count").assert(hasText("Dislikes :0"))
    }
    @Test
    fun testTagTextField(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-text-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-text-field").assert(hasText("Add tag"))
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-button").assertHasClickAction()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-button").assert(hasText("Validate"))
    }
    @Test
    fun testFeedbackTextField(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-text-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-text-field").assert(hasText("Add feedback"))
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-button").assertHasClickAction()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-button").assert(hasText("Validate"))
    }

    @Test
    fun testFeedbackList(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-list__title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-list__title").assert(hasText("Feedbacks : "))
    }

    @Test
    fun testTagList(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-list__title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-list__title").assert(hasText("Tags: "))
    }

    @Test
    fun feedbackListDisplaysCorrectly(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-test feedback1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-test feedback1").assert(hasText("- test feedback1"))
    }

    @Test
    fun tagListDisplaysCorrectly(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-test tag1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-test tag1").assert(hasText("- test tag1"))
    }

    @Test
    fun clickingLikeButtonIncrementsLikeCount(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__like").performClick()
        Thread.sleep(500)
        composeTestRule.onNodeWithTag("distraction-stat-screen__like-count").assert(hasText("Likes : 1"))
    }

    @Test
    fun clickingDislikeButtonIncrementsDislikeCount(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike").performClick()
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike-count").assert(hasText("Dislikes :1"))
    }

    @Test
    fun addingTagAddsTagToTagList(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-text-field").performTextInput("test tag2")
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-button").performClick()
        Thread.sleep(500)
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-test tag2").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-test tag2").assert(hasText("- test tag2"))
    }

    @Test
    fun addingFeedbackAddsFeedbackToFeedbackList(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-text-field").performTextInput("test feedback2")
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-button").performClick()
        Thread.sleep(500)
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-test feedback2").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-test feedback2").assert(hasText("- test feedback2"))
    }
}