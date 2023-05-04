package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.fakeServices.HistoryServiceFake
import com.github.studydistractor.sdp.history.HistoryViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val historyServiceFake = HistoryServiceFake()

    @Before
    fun setup() {
        composeTestRule.setContent {
            HistoryScreen(
                historyViewModel = HistoryViewModel(historyServiceFake)
            )
        }
    }

    @Test
    fun testHistoryClickable(){
        composeTestRule.onNodeWithTag("entry1000").performClick()
    }
}