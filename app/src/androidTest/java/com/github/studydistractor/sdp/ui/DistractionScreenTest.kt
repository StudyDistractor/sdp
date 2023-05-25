package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.bookmarks.BookmarksServiceFake
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.fakeServices.HistoryServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DistractionScreenTest {
    private val bookMarkService = BookmarksServiceFake()
    private val historyServiceFake = HistoryServiceFake()
    private val distractionViewModel = DistractionViewModel(bookMarkService,historyServiceFake)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        composeRule.setContent {
            DistractionScreen(distractionViewModel = distractionViewModel, {})
        }
    }

    @Test
    fun distractionIsDisplayed() {
        val name = "test"
        val description = "test description"
        val distraction = Distraction(name, description, distractionId = "id")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("name").assertExists()
        composeRule.onNodeWithTag("name").assert(hasText(name))
        composeRule.onNodeWithTag("description").assertExists()
        composeRule.onNodeWithTag("description").assert(hasText(description))
    }

    @Test
    fun buttonToCompleteActivityExistsAndHasCorrectText() {
        val distraction = Distraction("test", "test description", distractionId = "id")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("completeButton").assertExists()
        composeRule.onNodeWithTag("completeButton").assert(hasText("Activity completed!"))
        composeRule.onNodeWithTag("completeButton").assertIsDisplayed()
    }

    @Test
    fun iconIsNotDisplayedIfActivityHasNoIcon() {
        val distraction = Distraction("test", "test description", distractionId = "id")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("icon").assertDoesNotExist()
    }

    @Test
    fun iconIsDisplayedIfActivityHasIcon() {
        val distraction = Distraction("test", "test description", null, null, null, null, "bathtub_fill0_wght200_grad0_opsz48", distractionId = "id")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("icon").assertContentDescriptionEquals("bathtub_fill0_wght200_grad0_opsz48")
    }
    @Test
    fun testStatButtonIsDisplay(){
        val distraction = Distraction("test", "test description", null, null, null, null, "bathtub_fill0_wght200_grad0_opsz48", distractionId = "id")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("distraction-screen__stat-button").assertIsDisplayed()
        composeRule.onNodeWithTag("distraction-screen__stat-button").assertHasClickAction()
        composeRule.onNodeWithTag("distraction-screen__stat-button").performClick()
    }

    @Test
    fun bookmarkButtonWork(){
        val distraction = Distraction("test", "test description", null, null, null, null, "bathtub_fill0_wght200_grad0_opsz48", distractionId = "id")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("unbookmark-icon",true).assertIsDisplayed()
        composeRule.onNodeWithTag("bookmark-icon").assertDoesNotExist()
        composeRule.onNodeWithTag("distraction-screen__bookmark-button").assertIsDisplayed()
        composeRule.onNodeWithTag("distraction-screen__bookmark-button").assertHasClickAction()
    }


    @Test
    fun bookmarkButtonWork2(){
        val distraction = Distraction("test", "test description", 10.00,
            10.00, null, null, "bathtub_fill0_wght200_grad0_opsz48",
        distractionId = "test")
        val distraction2 = Distraction("test2", "test description", 10.00,
            10.00, null, null, "bathtub_fill0_wght200_grad0_opsz48",
            distractionId = "test2")
        bookMarkService.addDistractionToBookmark(distraction)

        distractionViewModel.updateDistraction(distraction)
        distractionViewModel.handleBookmark()
        distractionViewModel.onChangedBookmark()
        distractionViewModel.reverseBookmarked()
        distractionViewModel.onChangedBookmark()
        distractionViewModel.reverseBookmarked()
        distractionViewModel.distractionCompleted()

        distractionViewModel.updateDistraction(distraction2)
        distractionViewModel.handleBookmark()




    }


}