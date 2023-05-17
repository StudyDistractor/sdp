package com.github.studydistractor.sdp.ui


import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
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
        composeRule.onNodeWithTag("create-activity-screen__main-container").assertIsDisplayed()
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
        composeRule.onNodeWithTag("name").performTextInput("test name")
        composeRule.onNodeWithTag("description").performTextInput("")
        composeRule.onNodeWithTag("description").performTextInput("test description")
        composeRule.onNodeWithTag("name").assert(hasText("test name"))
        composeRule.onNodeWithTag("description").assert(hasText("test description"))
        composeRule.onNodeWithTag("nameSupport", useUnmergedTree = true).assert(hasText("9/20"))
        composeRule.onNodeWithTag("descriptionSupport", useUnmergedTree = true).assert(hasText("16/200"))
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

    @Test
    fun titleDisplaysCorrectly() {
        composeRule.onNodeWithTag("create-distraction-screen__title").assert(hasText("Create distraction"))
    }

    @Test
    fun checkBoxIsDisplayed() {
        composeRule.onNodeWithTag("checkbox").assertExists()
    }

    @Test
    fun locationRowIsDisplayed() {
        composeRule.onNodeWithTag("locationRow").assertExists()
    }

    @Test
    fun checkBoxIsNotCheckedByDefault() {
        composeRule.onNodeWithTag("checkbox").assertIsOff()
    }

    @Test
    fun checkBoxIsCheckedWhenClicked() {
        composeRule.onNodeWithTag("checkbox").assertIsOff()
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("checkbox").assertIsOn()
    }

    @Test
    fun checkBoxIsNotCheckedWhenClickedTwice() {
        composeRule.onNodeWithTag("checkbox").assertIsOff()
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("checkbox").assertIsOn()
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("checkbox").assertIsOff()
    }

    @Test
    fun latitudeAndLongitudeAreDisplayedWhenCheckBoxIsChecked() {
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").assertExists()
        composeRule.onNodeWithTag("longitude").assertExists()
    }

    @Test
    fun latitudeAndLongitudeAreNotDisplayedWhenCheckBoxIsNotChecked() {
        composeRule.onNodeWithTag("latitude").assertDoesNotExist()
        composeRule.onNodeWithTag("longitude").assertDoesNotExist()
    }

    @Test
    fun latitudeAndLongitudeAreNotDisplayedWhenCheckBoxIsCheckedAndUnchecked() {
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").assertExists()
        composeRule.onNodeWithTag("longitude").assertExists()
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").assertDoesNotExist()
        composeRule.onNodeWithTag("longitude").assertDoesNotExist()
    }

    @Test
    fun activityWithInvalidLatitudeIsNotInserted() {
        successCount = 0
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("91")
        composeRule.onNodeWithTag("longitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        assertEquals(0, successCount)
    }

    @Test
    fun activityWithInvalidLongitudeIsNotInserted() {
        successCount = 0
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("0")
        composeRule.onNodeWithTag("longitude").performTextInput("181")
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        assertEquals(0, successCount)
    }

    @Test
    fun activityWithValidLatitudeAndLongitudeIsInserted() {
        successCount = 0
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("0")
        composeRule.onNodeWithTag("longitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        assertEquals(1, successCount)
    }


    @Test
    fun activityWithOnlyLatitudeIsNotInserted() {
        successCount = 0
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        assertEquals(0, successCount)
    }

    @Test
    fun activityWithOnlyLongitudeIsNotInserted() {
        successCount = 0
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("longitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        assertEquals(0, successCount)
    }
}