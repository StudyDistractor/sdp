package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.user.FakeCreateUserModule.provideFakeCreateUserModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CreateUserScreenTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()
        composeRule.setContent {
            CreateUserScreen(
                {},
                userService = provideFakeCreateUserModule()
            )
        }
    }

    @Test
    fun testTextNode() {
        var firstName = "John"
        var lastName = "Doe"
        var phone = "0000000000"

        composeRule.onNodeWithTag("firstname").assertExists()
        composeRule.onNodeWithTag("firstname").assertIsDisplayed()
        composeRule.onNodeWithTag("firstname").performTextInput(firstName)
        composeRule.onNodeWithTag("firstname").assertTextContains(firstName)

        composeRule.onNodeWithTag("lastname").assertExists()
        composeRule.onNodeWithTag("lastname").assertIsDisplayed()
        composeRule.onNodeWithTag("lastname").performTextInput(lastName)
        composeRule.onNodeWithTag("lastname").assertTextContains(lastName)

        composeRule.onNodeWithTag("phone").assertExists()
        composeRule.onNodeWithTag("phone").assertIsDisplayed()
        composeRule.onNodeWithTag("phone").performTextInput(phone)
        composeRule.onNodeWithTag("phone").assertTextContains(phone)

    }

    @Test
    fun testValidButton(){
        composeRule.onNodeWithTag("validbutton").assertExists()
        composeRule.onNodeWithTag("validbutton").assertIsDisplayed()
        composeRule.onNodeWithTag("validbutton").assertHasClickAction()
        composeRule.onNodeWithTag("validbutton").performClick()
    }

    @Test
    fun testCalendarButton(){
        composeRule.onNodeWithTag("birthday").assertExists()
        composeRule.onNodeWithTag("birthday").assertIsDisplayed()
        composeRule.onNodeWithTag("birthday").assertHasClickAction()
        composeRule.onNodeWithTag("birthday").performClick()

    }


    @Test
    fun testValidButtonWithValues(){
        var firstName = "John"
        var lastName = "Doe"
        var phone = "0000000000"

        composeRule.onNodeWithTag("birthday").performClick()
        composeRule.onNodeWithTag("firstname").performTextInput(firstName)
        composeRule.onNodeWithTag("lastname").performTextInput(lastName)
        composeRule.onNodeWithTag("phone").performTextInput(phone)
        composeRule.onNodeWithTag("validbutton").performClick()
    }
}