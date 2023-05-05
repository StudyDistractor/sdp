package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppBarBottomTest {
    private val clicksNumber = List(4) { 0 }.toMutableList()

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        for(i in 0..3) { clicksNumber[i] = 0 }
        composeRule.setContent {
            AppBarBottom(
                onHomeClick = { clicksNumber[0]++ },
                onMapClick = { clicksNumber[1]++ },
                onListClick = { clicksNumber[2]++ },
                onMagicClick = { clicksNumber[3]++ },
                onFriendsClick = { clicksNumber[3]++ },

            )
        }
    }

    @Test
    fun testComponentsAppear() {
        composeRule.onNodeWithTag("app-bar-bottom__home-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__map-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__list-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__magic-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__friend-list-button").assertIsDisplayed()
    }

    @Test
    fun testPassingOnClickParameters() {
        for (i in 1..4) {
            composeRule.onNodeWithTag("app-bar-bottom__home-button").performClick()
            composeRule.onNodeWithTag("app-bar-bottom__map-button").performClick()
            composeRule.onNodeWithTag("app-bar-bottom__list-button").performClick()
            composeRule.onNodeWithTag("app-bar-bottom__magic-button").performClick()

            clicksNumber.forEach { assert(it == i) }
        }
    }
}