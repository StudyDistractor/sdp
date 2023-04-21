package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.fakeServices.RegisterServiceFake
import com.github.studydistractor.sdp.register.RegisterViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {
    private var successfullyRegistered = false

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        successfullyRegistered = false
        composeTestRule.setContent {
            RegisterScreen(
                onSuccess = { successfullyRegistered = true },
                registerViewModel = RegisterViewModel(RegisterServiceFake())
            )
        }
    }

    @Test
    fun mainContainerExists(){
        composeTestRule.onNodeWithTag("register-screen__main-container").assertIsDisplayed()
    }

    @Test
    fun mainContainerHasCorrectText(){
        composeTestRule.onNodeWithTag("register-screen__main-container").onChildAt(0).assertTextContains("Create an Account")
    }

    @Test
    fun emailFieldExists() {
        composeTestRule.onNodeWithTag("email").assertIsDisplayed()
    }

    @Test
    fun passwordFieldExists() {
        composeTestRule.onNodeWithTag("password").assertIsDisplayed()
    }

    @Test
    fun pseudoFieldExists() {
        composeTestRule.onNodeWithTag("pseudo").assertIsDisplayed()
    }

    @Test
    fun emailFieldHasCorrectLabel() {
        composeTestRule.onNodeWithTag("email").assert(hasText("Email"))
    }

    @Test
    fun passwordFieldHasCorrectLabel() {
        composeTestRule.onNodeWithTag("password").assert(hasText("Password"))
    }

    @Test
    fun pseudoFieldHasCorrectLabel() {
        composeTestRule.onNodeWithTag("pseudo").assert(hasText("Pseudo"))
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