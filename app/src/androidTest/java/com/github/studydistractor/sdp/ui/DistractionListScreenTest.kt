package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.distraction.Distraction
import com.github.studydistractor.sdp.distraction.DistractionListViewModel
import com.github.studydistractor.sdp.distraction.DistractionService
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.ui.DistractionListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DistractionListScreenTestTest {
    val name = "test"
    val description = "desc"
    val distractionViewmodel = DistractionViewModel()
    lateinit var distractionListViewModel: DistractionListViewModel

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var fakeService : DistractionService

    @Before
    fun setup() {
        rule.inject()
        distractionListViewModel = DistractionListViewModel(fakeService)
        val distraction = Distraction(name, description)
        fakeService.postDistraction(distraction)
        composeTestRule.setContent {
            DistractionListScreen({}, distractionViewmodel, distractionListViewModel)
        }
    }

    @Test
    fun distractionsAreDisplayedCorrectly() {
        composeTestRule.onNodeWithTag("distraction-list-screen__box-distraction").assertExists()
        composeTestRule.onNodeWithTag("name", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("name", useUnmergedTree = true).assert(hasText(name))
        composeTestRule.onNodeWithTag("distraction-list-screen__box-distraction").performClick()

        assertEquals(name, distractionViewmodel.distraction!!.name)
        assertEquals(description, distractionViewmodel.distraction!!.description)
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
        assertEquals(Distraction.Length.SHORT, distractionListViewModel.filterLength)
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Short").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        assertEquals(null, distractionListViewModel.filterLength)

        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Medium").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        assertEquals(Distraction.Length.MEDIUM, distractionListViewModel.filterLength)
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Medium").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        assertEquals(null, distractionListViewModel.filterLength)

        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Long").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        assertEquals(Distraction.Length.LONG, distractionListViewModel.filterLength)
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Long").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        assertEquals(null, distractionListViewModel.filterLength)
    }

    @Test
    fun applyTagsWorks() {
        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Food").assertExists()
        composeTestRule.onNodeWithText("Food").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        assert(distractionListViewModel.filterTags.contains("Food"))

        composeTestRule.onNodeWithTag("distraction-list-screen__filter-Button").performClick()
        composeTestRule.onNodeWithText("Food").assertExists()
        composeTestRule.onNodeWithText("Food").performClick()
        composeTestRule.onNodeWithTag("distraction-list-screen__button-apply-button").performClick()
        assert(!distractionListViewModel.filterTags.contains("Food"))
    }
}