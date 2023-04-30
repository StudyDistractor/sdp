package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.distraction.DistractionService
import com.github.studydistractor.sdp.distraction.DistractionTags
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

    private val tags = listOf( "Indoors", "Outdoors", "Online", "Offline", "Group", "Solo")

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
    fun tagsRowDisplaysCorrectly() {
        composeRule.onNodeWithTag("tags_row").assertIsDisplayed()
    }

    @Test
    fun tagsRowHasCorrectText() {
        composeRule.onNodeWithText("Tags").assertIsDisplayed()
    }

    @Test
    fun tagsAreDisplayedAndClickable(){
        val tag = tags.first().uppercase()
        composeRule.onNodeWithText(tag).assertIsDisplayed()
        composeRule.onNodeWithText(tag).assertHasClickAction()
    }

    @Test
    fun clickingOnTagSelectsAndDeselectsIt(){
        val tag = tags.first().uppercase()
        composeRule.onNodeWithText(tag).performClick()
        composeRule.onNodeWithText(tag).assertIsSelected()
        composeRule.onNodeWithText(tag).performClick()
        composeRule.onNodeWithText(tag).assertIsNotSelected()
    }

    @Test
    fun clickingOnTagAddsItToTheActivity(){
        val tag = tags.first().uppercase()
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithText(tag).performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(1, addedActivityList.size)
        val addedActivity = addedActivityList[0]
        Assert.assertEquals(1, addedActivity.tags?.size ?: 0)
        Assert.assertEquals(tag.toString(), addedActivity.tags?.get(0)?.uppercase() ?: "")
    }

    @Test
    fun clickingAndUnclickingOnTagDoesNotAddItToTheActivity(){
        val tag = tags.first().uppercase()
        val name = "test"
        val description = "test description"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithText(tag).performClick()
        composeRule.onNodeWithText(tag).performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchDistractions()
        Assert.assertEquals(1, addedActivityList.size)
        val addedActivity = addedActivityList[0]
        Assert.assertEquals(0, addedActivity.tags?.size ?: 0)
    }
}