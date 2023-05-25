package com.github.studydistractor.sdp.ui


import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppBarBottomTest {
    private val clicksNumber = List(6) { 0 }.toMutableList()

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        for(i in 0..5) { clicksNumber[i] = 0 }
        composeRule.setContent {
            AppBarBottom(
                onMapClick = { clicksNumber[0]++ },
                onListClick = { clicksNumber[1]++ },
                onMagicClick = { clicksNumber[2]++ },
                onEventListClick = {clicksNumber[3]++},
                onCreateDistractionActivityClick = {clicksNumber[4]++},
                onCreateEventActivityClick = {clicksNumber[5]++}
            )
        }
    }

    @Test
    fun testComponentsAppear() {
        composeRule.onNodeWithTag("app-bar-bottom").assertIsDisplayed()

        composeRule.onNodeWithTag("app-bar-bottom__list-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__list-button").assertHasClickAction()

        composeRule.onNodeWithTag("app-bar-bottom__magic-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__magic-button").assertHasClickAction()

        composeRule.onNodeWithTag("app-bar-bottom__map-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__map-button").assertHasClickAction()

        composeRule.onNodeWithTag("app-bar-bottom__create-distraction-activity-button").assertDoesNotExist()
        composeRule.onNodeWithTag("app-bar-bottom__create-event-activity-button").assertDoesNotExist()

        composeRule.onNodeWithTag("app-bar-bottom__create-activity-dropdown").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__create-activity-dropdown").performClick()

        composeRule.onNodeWithTag("app-bar-bottom__create-distraction-activity-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__create-distraction-activity-button").assertHasClickAction()
        composeRule.onNodeWithTag("app-bar-bottom__create-event-activity-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__create-event-activity-button").assertHasClickAction()
    }

    @Test
    fun testPassingOnClickParameters() {
        for (i in 1..4) {
            composeRule.onNodeWithTag("app-bar-bottom__list-button").performClick()
            composeRule.onNodeWithTag("app-bar-bottom__magic-button").performClick()
        }
    }
}