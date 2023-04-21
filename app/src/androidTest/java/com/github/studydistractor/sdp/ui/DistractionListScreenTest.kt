package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distractionList.DistractionListViewModel
import com.github.studydistractor.sdp.fakeServices.DistractionListServiceFake
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DistractionListScreenTestTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val distractionListServiceFake = DistractionListServiceFake()
    private val availableTags = distractionListServiceFake.getAvailableTags()
    private lateinit var lastDistractionClicked: Distraction
    private val distractionListViewModel = DistractionListViewModel(distractionListServiceFake)

    @Before
    fun setup() {
        composeTestRule.setContent {
            DistractionListScreen(
                onDistractionClicked = { lastDistractionClicked = it },
                distractionListViewModel = distractionListViewModel
            )
        }
        distractionListViewModel.showFilteredDistractions()
    }

    @Test
    fun distractionsAreDisplayedCorrectly() {
        distractionListServiceFake.distractions.forEach {
            composeTestRule.onNodeWithText(it.name!!).assertExists()
            composeTestRule.onNodeWithText(it.name!!).performClick()

            assertEquals(it, lastDistractionClicked)
        }
    }

    @Test
    fun clickOnFilterExpandColumn() {
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").assertExists()
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithTag("tags").assertExists()
        composeTestRule.onNodeWithTag("length").assertExists()
        composeTestRule.onNodeWithText("Length").assertExists()
        composeTestRule.onNodeWithText("Short").assertExists()
        composeTestRule.onNodeWithText("Medium").assertExists()
        composeTestRule.onNodeWithText("Long").assertExists()
    }

    @Test
    fun applyLengthsWorks() {
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Short").performClick()
        assertEquals(Distraction.Length.SHORT, distractionListViewModel.uiState.value.filtersSelectedLength)
        composeTestRule.onNodeWithText("Short").performClick()
        assertNull(distractionListViewModel.uiState.value.filtersSelectedLength)

        composeTestRule.onNodeWithText("Medium").performClick()
        assertEquals(Distraction.Length.MEDIUM, distractionListViewModel.uiState.value.filtersSelectedLength)
        composeTestRule.onNodeWithText("Medium").performClick()
        assertNull(distractionListViewModel.uiState.value.filtersSelectedLength)

        composeTestRule.onNodeWithText("Long").performClick()
        assertEquals(Distraction.Length.LONG, distractionListViewModel.uiState.value.filtersSelectedLength)
        composeTestRule.onNodeWithText("Long").performClick()
        assertNull(distractionListViewModel.uiState.value.filtersSelectedLength)
    }

    @Test
    fun applyTagsWorks() {
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("food").assertExists()
        composeTestRule.onNodeWithText("food").performClick()
        assert(distractionListViewModel.uiState.value.filtersSelectedTags.contains("food"))

        composeTestRule.onNodeWithText("food").assertExists()
        composeTestRule.onNodeWithText("food").performClick()
        assert(!distractionListViewModel.uiState.value.filtersSelectedTags.contains("food"))
    }
}