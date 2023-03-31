package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.register.FakeRegisterAuthModule.provideFakeRegisterAuth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@HiltAndroidTest
class RegisterScreenTest {
    var clicks = 0

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()

        clicks = 0
        composeTestRule.setContent {
            RegisterScreen(
                onRegistered = { clicks++ },
                registerAuth = provideFakeRegisterAuth()
            )
        }
    }


    @Test
    fun testRegisteredButtonFailsWithNoData() {
        composeTestRule.onNodeWithTag("register-screen__registered-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("register-screen__registered-button").assertHasClickAction()
        assertEquals(0, clicks)
        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
        assertEquals(0, clicks)
    }
    @Test
    fun testRegisterButton() {

        val email = "test@gmail.com"
        val password = "123456789qwertzuiop"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it

        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
    }
    @Test
    fun testRegisterButtonWithoutPassword() {

        val email = "test@gmail.com"
        val password = "123456789qwertzuiop"
        val pseudo = "test"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
//        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it

        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
    }
    @Test
    fun testRegisterButtonWithoutEmail() {

        val email = "test@gmail.com"
        val password = "123456789qwertzuiop"
        val pseudo = "test"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it

        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
    }
    @Test
    fun testRegisterButtonWithoutPseudo() {

        val email = "test@gmail.com"
        val password = "123456789qwertzuiop"
        val pseudo = "test"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
        composeTestRule.onNodeWithTag("password").performTextInput(password)

        // Find the "Register" button and click it

        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
    }
}