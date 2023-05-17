package com.github.studydistractor.sdp.ui

import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.createUser.CreateUserViewModel
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import com.github.studydistractor.sdp.eventHistory.EventHistoryViewModel
import com.github.studydistractor.sdp.fakeServices.CreateUserServiceFake
import com.github.studydistractor.sdp.fakeServices.EventChatServiceFake
import com.github.studydistractor.sdp.fakeServices.EventHistoryServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventHistoryScreenTest {
    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        composeRule.setContent {
            EventHistoryScreen(
                eventHistoryViewModel = EventHistoryViewModel(EventHistoryServiceFake()),
                chatViewModel = EventChatViewModel(EventChatServiceFake()),
                onChatButtonClicked = {})
        }
    }

    @Test
    fun testTitleIsDisplayed(){
        composeRule.onNodeWithTag("event-history-screen__title").assertIsDisplayed()
    }

    @Test
    fun testCardsThatShouldBeDisplayed(){
        composeRule.onNodeWithTag("event-history-card__title event1").assertExists()
        composeRule.onNodeWithTag("event-history-card__title event1").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__title event1").assertHasClickAction()

        composeRule.onNodeWithTag("event-history-card__title event2").assertExists()
        composeRule.onNodeWithTag("event-history-card__title event2").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__title event2").assertHasClickAction()

        composeRule.onNodeWithTag("event-history-card__title event3").assertExists()
        composeRule.onNodeWithTag("event-history-card__title event3").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__title event3").assertHasClickAction()
    }

    @Test
    fun testCardsThatShouldNotBeDisplayed(){
        composeRule.onNodeWithTag("event-history-card__title NotFinishedEvent").assertDoesNotExist()
        composeRule.onNodeWithTag("event-history-card__title UserHasNotTakenPartIn").assertDoesNotExist()
    }

}