package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.register.FakeRegisterAuthModule.provideFakeRegisterAuth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RegisterScreenTest {
    private var successfullyRegistered = false

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()

        successfullyRegistered = false
        composeTestRule.setContent {
            RegisterScreen(
                onRegistered = { successfullyRegistered = true },
                registerAuth = provideFakeRegisterAuth()
            )
        }
    }


    @Test
    fun testRegisteredButtonFailsWithNoData() {
        composeTestRule.onNodeWithTag("register-screen__registered-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("register-screen__registered-button").assertHasClickAction()
        assertEquals(false, successfullyRegistered)
        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
        try {
            composeTestRule.waitUntil(1000) {
                successfullyRegistered
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(false, successfullyRegistered)
    }
    @Test
    fun testRegisterButton() {

        val email = "test123@gmail.com"
        val password = "patate123"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it
        assertEquals(false, successfullyRegistered)
        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
        try {
            composeTestRule.waitUntil(1000) {
                successfullyRegistered
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(true, successfullyRegistered)
    }
    @Test
    fun testRegisterButtonWithoutPassword() {

        val email = "test@gmail.com"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
//        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it
        assertEquals(false, successfullyRegistered)
        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
        try {
            composeTestRule.waitUntil(1000) {
                successfullyRegistered
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(false, successfullyRegistered)
    }
    @Test
    fun testRegisterButtonWithoutEmail() {

        val password = "123456789qwertzuiop"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it
        assertEquals(false, successfullyRegistered)

        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
        try {
            composeTestRule.waitUntil(1000) {
                successfullyRegistered
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(false, successfullyRegistered)
    }
    @Test
    fun testRegisterButtonWithoutPseudo() {

        val email = "test@gmail.com"
        val password = "123456789qwertzuiop"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
        composeTestRule.onNodeWithTag("password").performTextInput(password)

        // Find the "Register" button and click it
        assertEquals(false, successfullyRegistered)
        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
        try {
            composeTestRule.waitUntil(1000) {
                successfullyRegistered
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(false, successfullyRegistered)
//        check if toast appears


    }

    @Test
    fun testRegistrationWithShortPassword() {
        val email = "test@gmail.com"
        val password = "a"
        // Find the email and password text fields and enter some text
        composeTestRule.onNodeWithTag("email").performTextInput(email)
        composeTestRule.onNodeWithTag("password").performTextInput(password)
        composeTestRule.onNodeWithTag("pseudo").performTextInput("test")

        // Find the "Register" button and click it
        assertEquals(false, successfullyRegistered)
        composeTestRule.onNodeWithTag("register-screen__registered-button").performClick()
        try {
            composeTestRule.waitUntil(1000) {
                successfullyRegistered
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(false, successfullyRegistered)
    }
}