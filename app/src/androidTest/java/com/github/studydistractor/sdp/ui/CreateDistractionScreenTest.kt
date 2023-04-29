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
    fun addIncompleteActivityDoesNotAddItToService1() {
        val name = "test"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("name").assert(hasText(name))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }

    @Test
    fun addIncompleteActivityDoesNotAddItToService2() {
        val description = "test"
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("description").assert(hasText(description))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }

    @Test
    fun tooLongNameDoesNotGetInserted() {
        val name = "test".repeat(100)
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("name").assertTextContains("0/20")
        composeRule.onNodeWithTag("description").assertTextContains(description)
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }

    @Test
    fun tooLongDescriptionDoesNotGetInserted() {
        val name = "test"
        val description = "test description".repeat(100)
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("name").assertTextContains(name)
        composeRule.onNodeWithTag("description").assertTextContains("0/200")
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }

    @Test
    fun tooLongNameAndDescriptionDoesNotGetInserted() {
        val name = "test".repeat(100)
        val description = "test description".repeat(100)
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("name").assertTextContains("0/20")
        composeRule.onNodeWithTag("description").assertTextContains("0/200")
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }

    @Test
    fun supportingTextDisplaysCorrectlyWhenEmpty() {
        composeRule.onNodeWithTag("name").assert(hasText(""))
        composeRule.onNodeWithTag("name").assert(hasText("0/20"))
        composeRule.onNodeWithTag("description").assert(hasText(""))
        composeRule.onNodeWithTag("description").assert(hasText("0/200"))
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
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("91")
        composeRule.onNodeWithTag("longitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
        composeRule.onNodeWithTag("latitude").performTextInput("invalid")
        val addedActivityList2 = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList2.size)
    }

    @Test
    fun activityWithInvalidLongitudeIsNotInserted() {
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("0")
        composeRule.onNodeWithTag("longitude").performTextInput("181")
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
        composeRule.onNodeWithTag("longitude").performTextInput("invalid")
        val addedActivityList2 = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList2.size)
    }

    @Test
    fun activityWithValidLatitudeAndLongitudeIsInserted() {
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("0")
        composeRule.onNodeWithTag("longitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(1, addedActivityList.size)
        val addedActivity = addedActivityList[0]
        Assert.assertEquals(name, addedActivity.name)
        Assert.assertEquals(description, addedActivity.description)
        Assert.assertEquals(0.0, addedActivity.lat)
        Assert.assertEquals(0.0, addedActivity.long)
    }

    @Test
    fun validLatitudeAndLongitudeAreNotInsertedWhenCheckBoxIsNotChecked() {
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("0")
        composeRule.onNodeWithTag("longitude").performTextInput("0")
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()

        Assert.assertEquals(1, addedActivityList.size)
        val addedActivity = addedActivityList[0]
        Assert.assertEquals(name, addedActivity.name)
        Assert.assertEquals(description, addedActivity.description)
        Assert.assertEquals(null, addedActivity.lat)
        Assert.assertEquals(null, addedActivity.long)
    }

    @Test
    fun activityWithOnlyLatitudeIsNotInserted() {
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("latitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }

    @Test
    fun activityWithOnlyLongitudeIsNotInserted() {
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("checkbox").performClick()
        composeRule.onNodeWithTag("longitude").performTextInput("0")
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(0, addedActivityList.size)
    }
}