package com.github.studydistractor.sdp.ui


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppBarBottomTest {
    var clicksNumber = List(4) { 0 }.toMutableList()

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()

        for(i in 0..3) { clicksNumber[i] = 0 }
        composeRule.setContent {
            AppBarBottom(
                onHomeClick = { clicksNumber[0]++ },
                onMapClick = { clicksNumber[1]++ },
                onListClick = { clicksNumber[2]++ },
                onMagicClick = { clicksNumber[3]++ },
            )
        }
    }

    @Test
    fun testComponentsAppear() {
        composeRule.onNodeWithTag("app-bar-bottom__home-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__map-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__list-button").assertIsDisplayed()
        composeRule.onNodeWithTag("app-bar-bottom__magic-button").assertIsDisplayed()
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