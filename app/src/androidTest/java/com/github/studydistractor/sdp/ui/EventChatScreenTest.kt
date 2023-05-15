package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import com.github.studydistractor.sdp.fakeServices.EventChatServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventChatScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeFriends = EventChatServiceFake()

    @Before
    fun setup() {
        composeTestRule.setContent {
            EventChatScreen(EventChatViewModel(fakeFriends))
        }
    }

    @Test
    fun titleIsShowed(){
        composeTestRule.onNodeWithTag("event-chat-screen__title").assertExists()
    }

    @Test
    fun messagesAreShowed(){
        composeTestRule.onNodeWithTag(
            "event-chat-screen__message-${fakeFriends.listOfMessages[0].messageId}"
        ).assertExists()
        composeTestRule.onNodeWithTag(
            "event-chat-screen__message-${fakeFriends.listOfMessages[1].messageId}"
        ).assertExists()
    }
    @Test
    fun iconButtonIsShowed(){
        composeTestRule.onNodeWithTag("event-chat-screen__icon-button").assertExists()
            .performClick()
    }

    @Test
    fun textFieldIsShowed(){
        composeTestRule.onNodeWithTag("event-chat-screen__text-field").assertExists()
            .performTextInput("test")
        composeTestRule.onNodeWithTag("event-chat-screen__icon-button").assertExists()
            .performClick()
    }
}