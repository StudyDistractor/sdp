package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppBarBottomTest {
    private val clicksNumber = List(6) { 0 }.toMutableList()

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        for(i in 0..5) { clicksNumber[i] = 0 }
        composeRule.setContent {
            AppBarBottom(
                onHomeClick = { clicksNumber[0]++ },
                onMapClick = { clicksNumber[1]++ },
                onListClick = { clicksNumber[2]++ },
                onMagicClick = { clicksNumber[3]++ },
                onEventListClick = {clicksNumber[3]++},
                onCreateDistractionActivityClick = {clicksNumber[4]++},
                onCreateEventActivityClick = {clicksNumber[5]++}
            )
        }
    }

    @Test
    fun testComponentsAppear() {
        composeRule.onNodeWithTag("app-bar-bottom__list-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__magic-button").assertIsDisplayed()
    }

    @Test
    fun testPassingOnClickParameters() {
        for (i in 1..4) {
            composeRule.onNodeWithTag("app-bar-bottom__list-button").performClick()
            composeRule.onNodeWithTag("app-bar-bottom__magic-button").performClick()
        }
    }
}