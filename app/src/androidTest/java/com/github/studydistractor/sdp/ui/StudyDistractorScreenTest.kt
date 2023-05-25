package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.StudyDistractorApp
import com.github.studydistractor.sdp.StudyDistractorScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StudyDistractorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            StudyDistractorApp()
        }
    }

    @Test
    fun mainNavigationDrawerDisplayed(){
        composeTestRule.onNodeWithTag("ModalNavigationDrawer").assertExists()
    }

    @Test
    fun modalDrawerSheetDisplayedCorrectly() {
        composeTestRule.onNodeWithTag("ModalDrawerSheet").assertExists()
        composeTestRule.onNodeWithTag("app-bar-top__drawer-button").performClick()
        composeTestRule.onNodeWithTag("ModalDrawerSheet").assertIsDisplayed()
        composeTestRule.onNodeWithTag("app-bar-top__title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("app-bar-top__title").assert(hasText("Study Distractor"))
    }

    @Test
    fun distractionHistoryButtonExistsAndWorks() {
        composeTestRule.onNodeWithTag("app-bar-top__drawer-button").performClick()
        composeTestRule.onNodeWithTag("app-bar-top__history-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("app-bar-top__history-button").assertHasClickAction()
        composeTestRule.onNodeWithTag("app-bar-top__history-button").assert(hasText("Distraction history"))
        composeTestRule.onNodeWithTag("app-bar-top__history-button").performClick()
        Thread.sleep(500)
        composeTestRule.onNodeWithTag("ModalDrawerSheet").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("HistoryScreen").assertIsDisplayed()
    }

    @Test
    fun eventHistoryButtonExistsAndWorks() {
        composeTestRule.onNodeWithTag("app-bar-top__drawer-button").performClick()
        composeTestRule.onNodeWithTag("app-bar-top__event-history-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("app-bar-top__event-history-button").assertHasClickAction()
        composeTestRule.onNodeWithTag("app-bar-top__event-history-button").assert(hasText("Event history"))
        composeTestRule.onNodeWithTag("app-bar-top__event-history-button").performClick()
        Thread.sleep(500)
        composeTestRule.onNodeWithTag("ModalDrawerSheet").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("EventHistoryScreen").assertIsDisplayed()
    }

    @Test
    fun friendsButtonExistsAndWorks() {
        composeTestRule.onNodeWithTag("app-bar-top__drawer-button").performClick()
        composeTestRule.onNodeWithTag("app-bar-top__friends-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("app-bar-top__friends-button").assertHasClickAction()
        composeTestRule.onNodeWithTag("app-bar-top__friends-button").assert(hasText("Friends"))
        composeTestRule.onNodeWithTag("app-bar-top__friends-button").performClick()
        Thread.sleep(500)
        composeTestRule.onNodeWithTag("ModalDrawerSheet").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("FriendsScreen").assertIsDisplayed()
    }

    @Test
    fun accountButtonExistsAndWorks() {
        composeTestRule.onNodeWithTag("app-bar-top__drawer-button").performClick()
        composeTestRule.onNodeWithTag("app-bar-top__account-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("app-bar-top__account-button").assertHasClickAction()
        composeTestRule.onNodeWithTag("app-bar-top__account-button").assert(hasText("Account"))
        composeTestRule.onNodeWithTag("app-bar-top__account-button").performClick()
        Thread.sleep(500)
        composeTestRule.onNodeWithTag("ModalDrawerSheet").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("login-screen__main-container").assertIsDisplayed()
    }

}