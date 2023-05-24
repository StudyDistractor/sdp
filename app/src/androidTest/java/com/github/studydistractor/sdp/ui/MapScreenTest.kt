package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.fakeServices.DistractionListServiceFake
import com.github.studydistractor.sdp.fakeServices.EventListServiceFake
import com.github.studydistractor.sdp.maps.MapViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {

        composeTestRule.setContent {
            MapsScreen(
                MapViewModel(
                    EventListServiceFake(),
                    DistractionListServiceFake()
                ),
                {},
                {}
            )
        }
    }
    @Test
    fun testMapDisplay(){
        composeTestRule.onNodeWithTag("maps-screen__main-container").assertIsDisplayed()
    }
}