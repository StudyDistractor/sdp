package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.studydistractor.sdp.procrastinationActivity.AddProcrastinationActivityActivity
import com.github.studydistractor.sdp.procrastinationActivity.ProcrastinationActivityService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

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
            assertEquals(1, it.size)
            val addedActivity = it[0]
            assertEquals(name, addedActivity.name)
            assertEquals(description, addedActivity.description)
        }
    }

    @Test
    fun addImcompleteActivityDoesNotAddItToService1() {
        val name = "test"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("name").assert(hasText(name))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchProcrastinationActivities {
            assertEquals(0, it.size)
        }
    }

    @Test
    fun addImcompleteActivityDoesNotAddItToService2() {
        val description = "test"
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("description").assert(hasText(description))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchProcrastinationActivities{
            assertEquals(0, it.size)
        }
    }
}