package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import com.github.studydistractor.sdp.eventHistory.EventHistoryViewModel
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
        val viewModel = EventHistoryViewModel(EventHistoryServiceFake())
        viewModel.setUserId("userId")

        composeRule.setContent {
            EventHistoryScreen(
                eventHistoryViewModel = viewModel,
                chatViewModel = EventChatViewModel(EventChatServiceFake()),
                onChatButtonClicked = {})
        }
    }

    @Test
    fun testTitleIsDisplayed(){
        composeRule.onNodeWithTag("event-history-screen__title").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-screen__title").assertTextContains("Your event history")
    }

    @Test
    fun testCardsThatShouldBeDisplayed(){
        composeRule.onNodeWithTag("event-history-card__title event1").assertExists()
        composeRule.onNodeWithTag("event-history-card__title event1").assertIsDisplayed()
//        composeRule.onNodeWithTag("event-history-card__title event1").assertHasClickAction()

        composeRule.onNodeWithTag("event-history-card__title event2").assertExists()
        composeRule.onNodeWithTag("event-history-card__title event2").assertIsDisplayed()
//        composeRule.onNodeWithTag("event-history-card__title event2").assertHasClickAction()

        composeRule.onNodeWithTag("event-history-card__title event3").assertExists()
        composeRule.onNodeWithTag("event-history-card__title event3").assertIsDisplayed()
//        composeRule.onNodeWithTag("event-history-card__title event3").assertHasClickAction()
    }

    @Test
    fun testChatButtonIsDisplayed(){
        composeRule.onNodeWithTag("event-history-card__chat-button event1").assertExists()
        composeRule.onNodeWithTag("event-history-card__chat-button event1").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__chat-button event1").assertHasClickAction()
        composeRule.onNodeWithTag("event-history-card__chat-button event1").assert(hasText("See Chat"))

        composeRule.onNodeWithTag("event-history-card__chat-button event2").assertExists()
        composeRule.onNodeWithTag("event-history-card__chat-button event2").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__chat-button event2").assertHasClickAction()
        composeRule.onNodeWithTag("event-history-card__chat-button event2").assert(hasText("See Chat"))

        composeRule.onNodeWithTag("event-history-card__chat-button event3").assertExists()
        composeRule.onNodeWithTag("event-history-card__chat-button event3").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__chat-button event3").assertHasClickAction()
        composeRule.onNodeWithTag("event-history-card__chat-button event3").assert(hasText("See Chat"))
    }

    @Test
    fun testPointsButtonIsDisplayed(){
        composeRule.onNodeWithTag("event-history-card__points-button event1").assertExists()
        composeRule.onNodeWithTag("event-history-card__points-button event1").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__points-button event1").assertHasClickAction()
        composeRule.onNodeWithTag("event-history-card__points-button event1").assert(hasText("Claim Points"))

        composeRule.onNodeWithTag("event-history-card__points-button event2").assertExists()
        composeRule.onNodeWithTag("event-history-card__points-button event2").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__points-button event2").assertHasClickAction()
        composeRule.onNodeWithTag("event-history-card__points-button event2").assert(hasText("Claim Points"))

        composeRule.onNodeWithTag("event-history-card__points-button event3").assertExists()
        composeRule.onNodeWithTag("event-history-card__points-button event3").assertIsDisplayed()
        composeRule.onNodeWithTag("event-history-card__points-button event3").assertHasClickAction()
        composeRule.onNodeWithTag("event-history-card__points-button event3").assert(hasText("Claim Points"))
    }

//    @Test
//    fun testEventHasName(){
//        composeRule.onNodeWithTag("event-history-card__title event1").assert(hasText("event1"))
//        composeRule.onNodeWithTag("event-history-card__title event2").assert(hasText("event2"))
//        composeRule.onNodeWithTag("event-history-card__title event3").assert(hasText("event3"))
//    }

    @Test
    fun testEventHasDate(){
        composeRule.onNodeWithTag("event-history-card__date event1", true).assert(hasText("From 10-10-1997 00:00 to 11-11-1997 00:00"))
        composeRule.onNodeWithTag("event-history-card__date event2", true).assert(hasText("From 10-10-1998 00:00 to 11-11-1998 00:00"))
        composeRule.onNodeWithTag("event-history-card__date event3", true).assert(hasText("From 10-10-1999 00:00 to 11-11-1999 00:00"))
    }

    @Test
    fun testEventHasDescription(){
        composeRule.onNodeWithTag("event-history-card__description event1", true).assert(hasText("event1"))
        composeRule.onNodeWithTag("event-history-card__description event2", true).assert(hasText("event2"))
        composeRule.onNodeWithTag("event-history-card__description event3", true).assert(hasText("event3"))
    }
}