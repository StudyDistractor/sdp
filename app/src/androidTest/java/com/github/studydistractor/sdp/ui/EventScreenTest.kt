package com.github.studydistractor.sdp.ui


import android.util.Log
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.data.Event
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

    private fun switchEvent(event: Event) {
        viewModel.setEventId(event)
    }

    @Before
    fun setup() {
        chatClicksCount = 0
        fakeService.currentEventId = "event0"
        switchEvent(fakeService.events["event0"]!!)
        composeTestRule.setContent {
            EventScreen(viewModel) { chatClicksCount++ }
        }
    }

    @Test
    fun eventWithNoParticipantsDisplayedAsExpected() {
        composeTestRule.onNodeWithTag("event-screen__name").assertTextContains("event0-name")
        composeTestRule.onNodeWithTag("event-screen__description").assertTextContains("event0-description")
        composeTestRule.onNodeWithTag("floating-action-buttons-screen__open-chat").assertDoesNotExist()
        composeTestRule.onNodeWithTag("floating-action-buttons-screen__toggle-participate").assertHasClickAction()
        composeTestRule.onNodeWithTag("event-screen__participants-headline").assertTextContains("Be the first to participate!", ignoreCase = true)
        composeTestRule.onNodeWithTag("event-screen__start").assertExists()
        composeTestRule.onNodeWithTag("event-screen__start").assertIsDisplayed()
        composeTestRule.onNodeWithTag("event-screen__start").assertTextContains("start: 01-01-1970 00:00")
        composeTestRule.onNodeWithTag("event-screen__end").assertExists()
        composeTestRule.onNodeWithTag("event-screen__end").assertIsDisplayed()
        composeTestRule.onNodeWithTag("event-screen__end").assertTextContains("end: 01-01-9999 00:00")
        composeTestRule.onNodeWithTag("event-screen__lateparticipation").assertExists()
        composeTestRule.onNodeWithTag("event-screen__lateparticipation").assertIsDisplayed()
        composeTestRule.onNodeWithTag("event-screen__lateparticipation").assertTextContains("late participation is allowed")
        composeTestRule.onNodeWithTag("event-screen__divider").assertExists()
        composeTestRule.onNodeWithTag("event-screen__divider").assertIsDisplayed()

    }

    @Test
    fun canParticipateToEventWithOtherParticipants() {
        composeTestRule.onNodeWithTag("event-screen__participant-user0").assertDoesNotExist()
        composeTestRule.onNodeWithTag("event-screen__participants-headline").assertTextContains("Be the first to participate!", ignoreCase = true)
        composeTestRule.onNodeWithTag("floating-action-buttons-screen__toggle-participate").performClick()
        composeTestRule.onNodeWithTag("event-screen__participants-headline").assertTextContains("Participants")
        composeTestRule.onNodeWithTag("event-screen__participant-user0").assertIsDisplayed()
    }

    @Test
    fun canWithdrawFromEvent() {
        switchEvent(fakeService.events["event1"]!!)
        composeTestRule.onNodeWithText("user0").assertIsDisplayed()
        composeTestRule.onNodeWithTag("floating-action-buttons-screen__toggle-participate").performClick()
        composeTestRule.onNodeWithTag("event-screen__participants-headline").assertTextContains("Be the first to participate!", ignoreCase = true)
        composeTestRule.onNodeWithText("user0").assertDoesNotExist()
    }

    @Test
    fun canGoToChat() {
        switchEvent(fakeService.events["event1"]!!)
        composeTestRule.onNodeWithTag("floating-action-buttons-screen__open-chat").performClick()
        assert(chatClicksCount == 1)
    }

    @Test
    fun toastDisplayWhenError(){
        fakeService.currentUserId = ""
        composeTestRule.onNodeWithTag("event-screen__participant-user0").assertDoesNotExist()
        composeTestRule.onNodeWithTag("event-screen__participants-headline").assertTextContains("Be the first to participate!", ignoreCase = true)
        composeTestRule.onNodeWithTag("floating-action-buttons-screen__toggle-participate").performClick()

    }
}
