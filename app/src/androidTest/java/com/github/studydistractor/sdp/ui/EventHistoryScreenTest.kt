package com.github.studydistractor.sdp.ui

import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.createUser.CreateUserViewModel
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import com.github.studydistractor.sdp.eventHistory.EventHistoryViewModel
import com.github.studydistractor.sdp.fakeServices.CreateUserServiceFake
import com.github.studydistractor.sdp.fakeServices.EventChatServiceFake
import com.github.studydistractor.sdp.fakeServices.EventHistoryServiceFake
import com.google.android.gms.tasks.Tasks
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

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
        composeRule.onNodeWithTag("event-history-screen__title").assertTextContains("Your event history")
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

class EventHistoryViewModelTest{
    val eventHistoryServiceFake = EventHistoryServiceFake()
    val eventHistoryViewModel = EventHistoryViewModel(eventHistoryModel = eventHistoryServiceFake)

    @Test
    fun testEmptyEvent(){
        Assert.assertThrows(ExecutionException::class.java) {
            Tasks.await( eventHistoryViewModel.claimPoints(""), 100, TimeUnit.MILLISECONDS)
        }
    }

    @Test
    fun testNoneExistingEvent(){
        Assert.assertThrows(ExecutionException::class.java) {
            Tasks.await( eventHistoryViewModel.claimPoints("event4"), 100, TimeUnit.MILLISECONDS)
        }
    }

    @Test
    fun testAlreadyClaimedPoint(){
        Assert.assertThrows(ExecutionException::class.java) {
            Tasks.await( eventHistoryViewModel.claimPoints("event2"), 100, TimeUnit.MILLISECONDS)
        }
    }

    @Test
    fun testUnClaimedPointWithExistingEventClaimPoint(){
        val old_size = eventHistoryServiceFake.eventClaimPoints.size
        // If it throws an exception then it should stop the test
        Tasks.await( eventHistoryViewModel.claimPoints("event1"), 100, TimeUnit.MILLISECONDS)

        Assert.assertEquals(1, eventHistoryServiceFake.currentUser.score)
        Assert.assertEquals(old_size, eventHistoryServiceFake.eventClaimPoints.size)
    }

    @Test
    fun testUnClaimedPointWithNonExistingEventClaimPoint(){
        val old_size = eventHistoryServiceFake.eventClaimPoints.size
        // If it throws an exception then it should stop the test
        Tasks.await( eventHistoryViewModel.claimPoints("event3"), 100, TimeUnit.MILLISECONDS)

        Assert.assertEquals(3,eventHistoryServiceFake.currentUser.score)
        Assert.assertEquals(old_size+ 1, eventHistoryServiceFake.eventClaimPoints.size)
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
    fun testChatButtonIsNotDisplayed(){
        composeRule.onNodeWithTag("event-history-card__chat-button NotFinishedEvent").assertDoesNotExist()
        composeRule.onNodeWithTag("event-history-card__chat-button UserHasNotTakenPartIn").assertDoesNotExist()
    }

    @Test
    fun testEventHasName(){
        composeRule.onNodeWithTag("event-history-card__title event1").assert(hasText("event1"))
        composeRule.onNodeWithTag("event-history-card__title event2").assert(hasText("event2"))
        composeRule.onNodeWithTag("event-history-card__title event3").assert(hasText("event3"))
    }

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