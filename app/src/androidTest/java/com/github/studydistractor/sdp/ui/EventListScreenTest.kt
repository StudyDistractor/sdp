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
        composeTestRule.onNodeWithTag("event-list-screen__event-card-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-card-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-name-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-name-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-description-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-description-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-card-column-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-card-column-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-row-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-row-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-start-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-start-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-end-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-end-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-row-count-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__event-row-count-id3", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__more-button-id2", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("event-list-screen__more-button-id3", useUnmergedTree = true).assertExists()
    }

    @Test
    fun clickOnEventCardIsRegistered() {
        assert(clickCount == 0)
        composeTestRule.onAllNodesWithTag("event-list-screen__more-button-id2", useUnmergedTree = true)[0].performClick()
        assert(clickCount == 1)
    }

    @Test
    fun eventThatHasStartedAndLateParticipationIsNotAllowedIsNotDisplayed(){
        composeTestRule.onNodeWithTag("event-list-screen__event-card-id1").assertDoesNotExist()
    }

    @Test
    fun eventThatHasStartedAndLateParticipationIsAllowedIsDisplayed(){
        composeTestRule.onNodeWithTag("event-list-screen__event-card-id2").assertExists()
    }
}