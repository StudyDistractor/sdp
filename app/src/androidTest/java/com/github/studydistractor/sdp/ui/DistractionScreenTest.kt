package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.distraction.Distraction
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DistractionScreenTest {
    private val name = "test"
    private val description = "desc"
    private val viewmodel = DistractionViewModel()

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()
     }

    @Test
    fun distractionIsDisplayedCorrectly() {
        val distraction = Distraction(name, description)
        viewmodel.addDistraction(distraction)
        composeTestRule.setContent {
            DistractionScreen(viewmodel)
        }
        composeTestRule.onNodeWithTag("name").assertExists()
        composeTestRule.onNodeWithTag("name").assert(hasText(name))
        composeTestRule.onNodeWithTag("description").assertExists()
        composeTestRule.onNodeWithTag("description").assert(hasText(description))
    }

    @Test
    fun buttonIsDisplayedCorrectly() {
        val distraction = Distraction(name, description)
        viewmodel.addDistraction(distraction)
        composeTestRule.setContent {
            DistractionScreen(viewmodel)
        }
        composeTestRule.onNodeWithTag("completeButton").assertExists()
    }

    @Test
    fun buttonHasCorrectText() {
        val distraction = Distraction(name, description)
        viewmodel.addDistraction(distraction)
        composeTestRule.setContent {
            DistractionScreen(viewmodel)
        }
        composeTestRule.onNodeWithTag("completeButton").assert(hasText("Activity completed!"))
    }



    @Test
    fun iconIsNotDisplayedInActivityWithoutIcon() {
        val distraction = Distraction(name, description)
        viewmodel.addDistraction(distraction)
        composeTestRule.setContent {
            DistractionScreen(viewmodel)
        }
        composeTestRule.onNodeWithTag("icon").assertDoesNotExist()
    }

    @Test
    fun iconIsDisplayedInActivityWithIcon() {
        val distraction = Distraction(name, description, null, null,"bathtub_fill0_wght200_grad0_opsz48")
        viewmodel.addDistraction(distraction)
        composeTestRule.setContent {
            DistractionScreen(viewmodel)
        }
        composeTestRule.onNodeWithTag("icon").assertExists()
    }

    @Test
    fun correctIconIsDisplayed() {
        val distraction = Distraction(name, description, null, null,"bathtub_fill0_wght200_grad0_opsz48")
        viewmodel.addDistraction(distraction)
        composeTestRule.setContent {
            DistractionScreen(viewmodel)
        }
        composeTestRule.onNodeWithTag("icon").assertContentDescriptionEquals("bathtub_fill0_wght200_grad0_opsz48")
    }
}