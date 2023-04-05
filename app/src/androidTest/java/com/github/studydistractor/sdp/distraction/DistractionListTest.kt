package com.github.studydistractor.sdp.distraction

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.ui.DistractionListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DistractionListTest {
    val name = "test"
    val description = "desc"
    val distractionViewmodel = DistractionViewModel()
    lateinit var distractionListViewModel: DistractionListViewModel

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var fakeService : DistractionService

    @Before
    fun setup() {
        rule.inject()
        distractionListViewModel = DistractionListViewModel(fakeService)
        val distraction = Distraction(name, description)
        fakeService.postDistraction(distraction)
        composeTestRule.setContent {
            DistractionListScreen({}, distractionViewmodel, distractionListViewModel)
        }
    }

    @Test
    fun distractionsAreDisplayedCorrectly() {
        composeTestRule.onNodeWithTag("distractionLayout").assertExists()
        composeTestRule.onNodeWithTag("name", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("name", useUnmergedTree = true).assert(hasText(name))
        composeTestRule.onNodeWithTag("distractionLayout").performClick()

        assertEquals(name, distractionViewmodel.distraction!!.name)
        assertEquals(description, distractionViewmodel.distraction!!.description)
    }
}