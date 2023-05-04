package com.github.studydistractor.sdp.ui


import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.createDistraction.CreateDistractionViewModel
import com.github.studydistractor.sdp.fakeServices.CreateDistractionServiceFake
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreateDistractionScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    private val createDistractionServiceFake = CreateDistractionServiceFake()
    private val viewModel = CreateDistractionViewModel(createDistractionServiceFake)
    private var successCount = 0

    @Before
    fun setup() {
        successCount = 0
        composeRule.setContent {
            CreateDistractionScreen(
                createDistractionViewModel = viewModel,
                onDistractionCreated = { successCount++ })
        }
    }

    @Test
    fun testScreenIsShown() {
        composeRule.onNodeWithTag("create-distraction-screen__main-container").assertIsDisplayed()
        composeRule.onNodeWithText("Create new distraction").assertIsDisplayed()
    }

    @Test
    fun addSimpleActivityAddItToTheService() {
        successCount = 0

        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("name").assert(hasText(name))

        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("description").assert(hasText(description))

        composeRule.onNodeWithTag("addActivity").performClick()

        Thread.sleep(500) // bad but does the job.
        assertEquals(1, successCount)
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalTestApi::class)
    @Test
    fun supportingTextDisplaysCorrectlyWhenEmpty() {
        composeRule.onNodeWithTag("name").performTextInput("test")
        composeRule.onNodeWithTag("description").performTextInput("test")
        composeRule.onNodeWithTag("name").assert(hasText("test"))
        composeRule.onNodeWithTag("description").assert(hasText("test"))
        composeRule.onNodeWithTag("nameSupport", useUnmergedTree = true).assert(hasText("4/20"))
        composeRule.onNodeWithTag("descriptionSupport", useUnmergedTree = true).assert(hasText("4/200"))
    }

    @Test
    fun supportingTextDisplaysCorrectlyWhenNotEmpty() {
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("name").assert(hasText(name))
        composeRule.onNodeWithTag("name").assert(hasText("${name.length}/20"))
        composeRule.onNodeWithTag("description").assert(hasText(description))
        composeRule.onNodeWithTag("description").assert(hasText("${description.length}/200"))
    }
}