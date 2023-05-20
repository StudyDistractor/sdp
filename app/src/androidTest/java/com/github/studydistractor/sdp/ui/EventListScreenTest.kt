package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import com.github.studydistractor.sdp.eventList.EventListViewModel
import com.github.studydistractor.sdp.fakeServices.EventChatServiceFake
import com.github.studydistractor.sdp.fakeServices.EventListServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val eventListService = EventListServiceFake()
    private var clickCount = 0
    @Before
    fun setup() {
        composeTestRule.setContent {
            EventListScreen(onEventClicked = {clickCount++}, eventListViewModel = EventListViewModel(eventListService))
        }
    }

    @Test
    fun eventElementsAreDisplayed() {
        composeTestRule.onAllNodesWithTag("event-list-screen__event-card", useUnmergedTree = true)[0].assertExists()
        composeTestRule.onAllNodesWithTag("event-list-screen__event-name-id", useUnmergedTree = true)[0].assertExists()
        composeTestRule.onAllNodesWithTag("event-list-screen__event-description", useUnmergedTree = true)[0].assertExists()
        composeTestRule.onAllNodesWithTag("event-list-screen__event-start", useUnmergedTree = true)[0].assertExists()
        composeTestRule.onAllNodesWithTag("event-list-screen__event-end", useUnmergedTree = true)[0].assertExists()
    }

    @Test
    fun clickOnEventCardIsRegistered() {
        assert(clickCount == 0)
        composeTestRule.onAllNodesWithTag("event-list-screen__more-button", useUnmergedTree = true)[0].performClick()
        assert(clickCount == 1)
    }
}