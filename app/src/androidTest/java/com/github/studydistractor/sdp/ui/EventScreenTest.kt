package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.event.EventViewModel
import com.github.studydistractor.sdp.fakeServices.EventServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val fakeService = EventServiceFake()
    val viewModel = EventViewModel(fakeService)

    var chatClicksCount = 0

//    private fun switchEvent(eventId: String) {
//        viewModel.setEventId(eventId)
//        fakeService.triggerEventChange(fakeService.events[eventId]!!)
//        fakeService.triggerParticipantsChange(fakeService
//            .eventParticipants[eventId]!!
//            .filterValues { it }
//            .keys
//            .toList()
//        )
//    }

//    @Before
//    fun setup() {
//        chatClicksCount = 0
//        fakeService.triggerUserIdChange("user0")
//        switchEvent("event0")
//        composeTestRule.setContent {
//            EventScreen(viewModel) { chatClicksCount++ }
//        }
//    }

    @Test
    fun eventWithNoParticipantsDisplayedAsExpected() {
        composeTestRule.onNodeWithTag("event-name").assertTextContains("event0-name")
        composeTestRule.onNodeWithTag("event-description").assertTextContains("event0-description")
        composeTestRule.onNodeWithTag("open-chat").assertDoesNotExist()
        composeTestRule.onNodeWithTag("toggle-participate").assertHasClickAction()
    }

    @Test
    fun canParticipateToEventWithOtherParticipants() {
        composeTestRule.onNodeWithText("user0").assertDoesNotExist()
        composeTestRule.onNodeWithTag("event-participants-headline").assertTextContains("Be the first to participate!", ignoreCase = true)
        composeTestRule.onNodeWithTag("toggle-participate").performClick()
        composeTestRule.onNodeWithTag("event-participants-headline").assertTextContains("Participants")
        composeTestRule.onNodeWithText("user0").assertExists()
    }

//    @Test
//    fun canWithdrawFromEvent() {
//        switchEvent("event1")
//        composeTestRule.onNodeWithText("user0").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("toggle-participate").performClick()
//        composeTestRule.onNodeWithTag("event-participants-headline").assertTextContains("Be the first to participate!", ignoreCase = true)
//        composeTestRule.onNodeWithText("user0").assertIsNotDisplayed()
//    }

//    @Test
//    fun canGoToChat() {
//        switchEvent("event1")
//        composeTestRule.onNodeWithTag("open-chat").performClick()
//        assert(chatClicksCount == 1)
//    }
}