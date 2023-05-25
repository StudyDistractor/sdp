package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.dailyChallenge.DailyChallengeViewModel
import com.github.studydistractor.sdp.fakeServices.DailyChallengeServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DailyChallengeScreenTest {
    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()
    val fakeService = DailyChallengeServiceFake()

    val viewModel =DailyChallengeViewModel(fakeService)

    @Before
    fun setup() {
        composeTestRule.setContent {
            DailyChallengeScreen(DailyChallengeViewModel(DailyChallengeServiceFake()))
        }
    }

    @Test
    fun mainContainerIsDisplayed() {
        composeTestRule.onNodeWithTag("daily-challenge-screen__main-container").assertExists()
    }

    @Test
    fun descriptionIsDisplayed() {
        composeTestRule.onNodeWithTag("daily-challenge-screen__description").assertExists()
        composeTestRule.onNodeWithTag("daily-challenge-screen__description").assert(hasText("Complete these activities and earn the honour of being a master procrastinator."))
    }

    @Test
    fun distractionCardsAreDisplayed() {
        composeTestRule.onNodeWithTag("distraction-card-for-Name1").assertExists()
        composeTestRule.onNodeWithTag("distraction-card-for-Name2").assertExists()
        composeTestRule.onNodeWithTag("distraction-card-for-Name3").assertExists()
    }

    @Test
    fun distractionNamesAndDescriptionsAreDisplayed() {
        composeTestRule.onNodeWithText("Name1").assertExists()
        composeTestRule.onNodeWithText("Name2").assertExists()
        composeTestRule.onNodeWithText("Name3").assertExists()
        composeTestRule.onNodeWithText("Description1").assertExists()
        composeTestRule.onNodeWithText("Description2").assertExists()
        composeTestRule.onNodeWithText("Description3").assertExists()
    }

    @Test
    fun checkBoxesAreDisplayedAndClickable() {
        composeTestRule.onNodeWithTag("distraction-card__checkbox-for-Name1").assertExists()
        composeTestRule.onNodeWithTag("distraction-card__checkbox-for-Name2").assertExists()
        composeTestRule.onNodeWithTag("distraction-card__checkbox-for-Name3").assertExists()
        composeTestRule.onNodeWithTag("distraction-card__checkbox-for-Name1").assertHasClickAction()
        composeTestRule.onNodeWithTag("distraction-card__checkbox-for-Name2").assertHasClickAction()
        composeTestRule.onNodeWithTag("distraction-card__checkbox-for-Name3").assertHasClickAction()
    }

    @Test
    fun testKonfetti() {
        composeTestRule.onNodeWithTag("daily-challenge-screen__konfetti").assertDoesNotExist()
    }

    @Test
    fun testCheckBoxWorks(){
        viewModel.onCheckboxClicked(0, true)
    }
}