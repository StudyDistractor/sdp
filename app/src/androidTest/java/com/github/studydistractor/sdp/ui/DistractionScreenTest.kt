package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.fakeServices.DistractionServiceFake
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DistractionScreenTest {
    private val distractionViewModel = DistractionViewModel(DistractionServiceFake())

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        composeRule.setContent {
            DistractionScreen(distractionViewModel = distractionViewModel)
        }
    }

    @Test
    fun distractionIsDisplayed() {
        val name = "test"
        val description = "test description"
        val distraction = Distraction(name, description)
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("name").assertExists()
        composeRule.onNodeWithTag("name").assert(hasText(name))
        composeRule.onNodeWithTag("description").assertExists()
        composeRule.onNodeWithTag("description").assert(hasText(description))
    }

    @Test
    fun buttonToCompleteActivityExistsAndHasCorrectText() {
        val distraction = Distraction("test", "test description")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("completeButton").assertExists()
        composeRule.onNodeWithTag("completeButton").assert(hasText("Activity completed!"))
    }

    @Test
    fun iconIsNotDisplayedIfActivityHasNoIcon() {
        val distraction = Distraction("test", "test description")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("icon").assertDoesNotExist()
    }

    @Test
    fun iconIsDisplayedIfActivityHasIcon() {
        val distraction = Distraction("test", "test description", null, null, null, null, "bathtub_fill0_wght200_grad0_opsz48")
        distractionViewModel.updateDistraction(distraction)
        composeRule.onNodeWithTag("icon").assertContentDescriptionEquals("bathtub_fill0_wght200_grad0_opsz48")
    }
}