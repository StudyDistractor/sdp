package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.bookmarks.BookmarksServiceFake
import com.github.studydistractor.sdp.distractionList.DistractionListViewModel
import com.github.studydistractor.sdp.fakeServices.DistractionListServiceFake
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DistractionListScreenTestTest {
    private lateinit var distractionListViewModel: DistractionListViewModel

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        distractionListViewModel = DistractionListViewModel(bookmarkService = BookmarksServiceFake(), distractionListModel = DistractionListServiceFake())
        composeTestRule.setContent {
            DistractionListScreen({}, distractionListViewModel)
        }
    }

    @Test
    fun distractionsAreDisplayedCorrectly() {
        composeTestRule.onNodeWithText("testDistraction", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("testDistraction", useUnmergedTree = true).performClick()
    }

    @Test
    fun clickOnFilterExpandColumn() {
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").assertExists()
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithTag("filterIsOpen").assertExists()
        composeTestRule.onNodeWithTag("tags").assertExists()
        composeTestRule.onNodeWithTag("length").assertExists()
        composeTestRule.onNodeWithText("Length").assertExists()
        composeTestRule.onNodeWithText("Short").assertExists()
        composeTestRule.onNodeWithText("Medium").assertExists()
        composeTestRule.onNodeWithText("Long").assertExists()
        composeTestRule.onNodeWithText("Apply Filters").assertExists()
    }

    @Test
    fun applyLengthsWorks() {
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Short").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        composeTestRule.onAllNodesWithText("shortDistraction", useUnmergedTree = true)[0].assertExists()
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Short").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()

        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Medium").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        composeTestRule.onAllNodesWithText("mediumDistraction", useUnmergedTree = true)[0].assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Medium").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()

        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Long").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        composeTestRule.onAllNodesWithText("longDistraction", useUnmergedTree = true)[0].assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Long").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
    }

    @Test
    fun applyTagsWorks() {
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Food").assertExists()
        composeTestRule.onNodeWithText("Food").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        composeTestRule.onAllNodesWithText("foodDistraction")[0].assertExists()
    }

    @Test
    fun bookmarksIsDisplayed() {
        composeTestRule.onNodeWithTag("distraction-list-screen__bookmarked-icon", useUnmergedTree = true).assertExists()
    }

}