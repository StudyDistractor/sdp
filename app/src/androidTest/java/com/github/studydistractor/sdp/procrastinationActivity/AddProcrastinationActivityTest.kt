package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.github.studydistractor.sdp.procrastinationActivity.AddProcrastinationActivityActivity
import com.github.studydistractor.sdp.procrastinationActivity.ProcrastinationActivityService

@HiltAndroidTest
class AddProcrastinationActivityTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<AddProcrastinationActivityActivity>()

    @Inject
    lateinit var fakeService: ProcrastinationActivityService

    @Before
    fun setup() {
        rule.inject()
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
        val addedActivityList = fakeService.fetchProcrastinationActivities {
            Thread.sleep(1000)
            assertEquals(1, it.size)
            val addedActivity = it[0]
            assertEquals(name, addedActivity.name)
            assertEquals(description, addedActivity.description)
        }
    }

    @Test
    fun addIncompleteActivityDoesNotAddItToService1() {
        val name = "test"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("name").assert(hasText(name))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchProcrastinationActivities {
            assertEquals(0, it.size)
        }
    }

    @Test
    fun addIncompleteActivityDoesNotAddItToService2() {
        val description = "test"
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("description").assert(hasText(description))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchProcrastinationActivities{
            assertEquals(0, it.size)
        }
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
        val addedActivityList = fakeService.fetchProcrastinationActivities {
            assertEquals(0, it.size)
        }
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
        val addedActivityList = fakeService.fetchProcrastinationActivities {
            assertEquals(0, it.size)
        }
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
        val addedActivityList = fakeService.fetchProcrastinationActivities {
            assertEquals(0, it.size)
        }
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

}