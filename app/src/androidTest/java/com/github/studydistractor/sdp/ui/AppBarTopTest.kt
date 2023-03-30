package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.StudyDistractorScreen
import com.github.studydistractor.sdp.login.FakeLoginAuth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppBarTopTest {


    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun testTitleAppears() {
        composeRule.setContent {
            AppBarTop(
                currentScreen = StudyDistractorScreen.values()[0],
                canNavigateBack = false,
                navigateUp = { throw Error("this should never be called") },
                goToMapActivity = { /* this must not be tested as it is temporary */ },
                goToDistractionActivity = { /* this must not be tested as it is temporary */ },
                goToCreateDistractionActivity = { /* this must not be tested as it is temporary */ })
        }

        composeRule.onNodeWithTag("app-bar-top__title").assertIsDisplayed()
    }

    @Test
    fun testCannotGoBackWorks() {
        composeRule.setContent {
            AppBarTop(
                currentScreen = StudyDistractorScreen.values()[0],
                canNavigateBack = false,
                navigateUp = { throw Error("this should never be called") },
                goToMapActivity = { /* this must not be tested as it is temporary */ },
                goToDistractionActivity = { /* this must not be tested as it is temporary */ },
                goToCreateDistractionActivity = { /* this must not be tested as it is temporary */ })
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
                goToMapActivity = { /* this must not be tested as it is temporary */ },
                goToDistractionActivity = { /* this must not be tested as it is temporary */ },
                goToCreateDistractionActivity = { /* this must not be tested as it is temporary */ })
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