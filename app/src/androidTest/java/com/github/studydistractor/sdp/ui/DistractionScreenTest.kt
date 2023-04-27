package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.distraction.Distraction
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.history.FakeHistoryModule
import com.github.studydistractor.sdp.history.HistoryInterface
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DistractionScreenTest {

    private val distractionViewModel = DistractionViewModel()

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    lateinit var historyInterface : HistoryInterface

    @Before
    fun setup() {
        rule.inject()
        historyInterface = FakeHistoryModule().provideFakeHistoryInterface()
    }

    @Test
    fun distractionIsDisplayed() {
        val name = "test"
        val description = "test description"
        val distraction = Distraction(name, description)
        distractionViewModel.addDistraction(distraction)
        composeRule.setContent {
            DistractionScreen(distractionViewModel, historyInterface)
        }
        composeRule.onNodeWithTag("name").assertExists()
        composeRule.onNodeWithTag("name").assert(hasText(name))
        composeRule.onNodeWithTag("description").assertExists()
        composeRule.onNodeWithTag("description").assert(hasText(description))
    }

    @Test
    fun buttonToCompleteActivityExistsAndHasCorrectText() {
        val distraction = Distraction("test", "test description")
        distractionViewModel.addDistraction(distraction)
        composeRule.setContent {
            DistractionScreen(distractionViewModel, historyInterface)
        }
        composeRule.onNodeWithTag("completeButton").assertExists()
        composeRule.onNodeWithTag("completeButton").assert(hasText("Activity completed!"))
    }

    @Test
    fun iconIsNotDisplayedIfActivityHasNoIcon() {
        val distraction = Distraction("test", "test description")
        distractionViewModel.addDistraction(distraction)
        composeRule.setContent {
            DistractionScreen(distractionViewModel, historyInterface)
        }
        composeRule.onNodeWithTag("icon").assertDoesNotExist()
    }

    @Test
    fun iconIsDisplayedIfActivityHasIcon() {
        val distraction = Distraction("test", "test description", null, null, null, null, "bathtub_fill0_wght200_grad0_opsz48")
        distractionViewModel.addDistraction(distraction)
        composeRule.setContent {
            DistractionScreen(distractionViewModel, historyInterface)
        }
        composeRule.onNodeWithTag("icon").assertContentDescriptionEquals("bathtub_fill0_wght200_grad0_opsz48")
    }
}