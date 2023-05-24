package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.StudyDistractorScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppBarTopTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
    }

    @Test
    fun testCannotGoBackWorks() {
        composeRule.setContent {
            AppBarTop(
                currentScreen = StudyDistractorScreen.values()[0],
                canNavigateBack = false,
                navigateUp = { throw Error("this should never be called") },
                openDrawer = {})
        }

        composeRule.onNodeWithTag("app-bar-top__back-button").assertDoesNotExist()
    }

    @Test
    fun testGoingBackButtonWorks() {
        var backClicks = 0

        composeRule.setContent {
            AppBarTop(
                currentScreen = StudyDistractorScreen.values()[0],
                canNavigateBack = true,
                navigateUp = { backClicks++ },
                openDrawer = {}
            )
        }

        composeRule.onNodeWithTag("app-bar-top__back-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-top__back-button").assertHasClickAction()
        assert(backClicks == 0)
        composeRule.onNodeWithTag("app-bar-top__back-button").performClick()
        assert(backClicks == 1)
        composeRule.onNodeWithTag("app-bar-top__back-button").performClick()
        assert(backClicks == 2)
    }
}