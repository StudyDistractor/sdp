package com.github.studydistractor.sdp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
        val addedActivityList = fakeService.fetchProcrastinationActivities()

        assertEquals(1, addedActivityList.size)
        val addedActivity = addedActivityList[0]
        assertEquals(name, addedActivity.name)
        assertEquals(description, addedActivity.description)

    }

    @Test
    fun addIncompleteActivityDoesNotAddItToService1() {
        val name = "test"
        composeRule.onNodeWithTag("name").performTextInput(name)
        composeRule.onNodeWithTag("name").assert(hasText(name))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchProcrastinationActivities()

        assertEquals(0, addedActivityList.size)

    }

    @Test
    fun addIncompleteActivityDoesNotAddItToService2() {
        val description = "test"
        composeRule.onNodeWithTag("description").performTextInput(description)
        composeRule.onNodeWithTag("description").assert(hasText(description))

        composeRule.onNodeWithTag("addActivity").performClick()
        val addedActivityList = fakeService.fetchProcrastinationActivities()

        assertEquals(0, addedActivityList.size)
    }
}