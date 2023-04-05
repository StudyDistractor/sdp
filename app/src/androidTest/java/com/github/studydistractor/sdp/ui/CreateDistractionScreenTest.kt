package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.distraction.DistractionService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CreateDistractionScreenTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Inject
    lateinit var fakeService: DistractionService

    @Before
    fun setup() {
        rule.inject()
        composeRule.setContent {
            CreateDistractionScreen(fakeService)
        }
    }

    @Test
    fun testScreenIsShown() {
        composeRule.onNodeWithTag("create-distraction-screen__main-container").assertIsDisplayed()
        composeRule.onNodeWithText("Create new distraction").assertIsDisplayed()
    }

    @Test
    fun addSimpleActivityAddItToTheService() {
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("name").assert(hasText(name))

        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("description").assert(hasText(description))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(1, addedActivityList.size)
        val addedActivity = addedActivityList[0]
        Assert.assertEquals(name, addedActivity.name)
        Assert.assertEquals(description, addedActivity.description)
    }

    @Test
    fun addImcompleteActivityDoesNotAddItToService1() {
        val name = "test"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("name").assert(hasText(name))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }

    @Test
    fun addImcompleteActivityDoesNotAddItToService2() {
        val description = "test"
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("description").assert(hasText(description))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }
}