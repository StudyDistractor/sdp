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
import com.github.studydistractor.sdp.fakeServices.LoginServiceFake
import com.github.studydistractor.sdp.login.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    private var registerButtonClicks = 0
    private var loggedInCount = 0

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        registerButtonClicks = 0
        loggedInCount = 0
        composeRule.setContent {
            LoginScreen(
                onRegisterButtonClicked = { registerButtonClicks++ },
                onLoggedIn = { loggedInCount++ },
                loginViewModel = LoginViewModel(LoginServiceFake())
            )
        }
    }

    @Test
    fun mainContainerExists() {
        composeRule.onNodeWithTag("login-screen__main-container").assertIsDisplayed()
    }

    @Test
    fun mainContainerHasCorrectText() {
        composeRule.onNodeWithTag("login-screen__main-container").onChildAt(0).assertTextContains("Log In")
    }

    @Test
    fun emailFieldExists() {
        composeRule.onNodeWithTag("email").assertIsDisplayed()
    }

    @Test
    fun passwordFieldExists() {
        composeRule.onNodeWithTag("password").assertIsDisplayed()
    }

    @Test
    fun loginButtonExists() {
        composeRule.onNodeWithTag("login").assertIsDisplayed()
    }

    @Test
    fun loginButtonHasCorrectText() {
        composeRule.onNodeWithTag("login").assert(hasText("Log in"))
    }

    @Test
    fun registerButtonHasCorrectText() {
        composeRule.onNodeWithTag("register").assert(hasText("Register"))
    }

    @Test
    fun loginButtonCanBeClicked() {
        composeRule.onNodeWithTag("login").assertHasClickAction()
    }

    @Test
    fun emailFieldHasCorrectHint() {
        composeRule.onNodeWithTag("email").assert(hasText("Email"))
    }

    @Test
    fun passwordFieldHasCorrectHint() {
        composeRule.onNodeWithTag("password").assert(hasText("Password"))
    }

    @Test
    fun testToLoginWithValidEmailAndPassword() {
        val email = "validEmail@email.com"
        val password = "validPassword"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("password").performTextInput(password)

        assertEquals(0, loggedInCount)
        composeRule.onNodeWithTag("login").performClick()
        try {
            composeRule.waitUntil(1000) {
                loggedInCount == 1
            }
        } catch (_: ComposeTimeoutException) {
        }

        assertEquals(0, registerButtonClicks)
        assertEquals(1, loggedInCount)
    }

    @Test
    fun testRegisterButtonWorks() {
        composeRule.onNodeWithTag("register").assertIsDisplayed()
        composeRule.onNodeWithTag("register").assertHasClickAction()

        assertEquals(0, registerButtonClicks)
        assertEquals(0, loggedInCount)
        composeRule.onNodeWithTag("register").performClick()
        assertEquals(1, registerButtonClicks)
        assertEquals(0, loggedInCount)
    }

    @Test
    fun testToLoginWithInvalidEmailAndPassword() {
        val email = "invalidEmail"
        val password = "invalidPassword"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("password").performTextInput(password)
        composeRule.onNodeWithTag("login").performClick()
        try {
            composeRule.waitUntil(1000) {
                loggedInCount == 1
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(0, registerButtonClicks)
        assertEquals(0, loggedInCount)
    }

    @Test
    fun testToLoginWithoutEmail() {
        val password = "1234567890"
        composeRule.onNodeWithTag("password").performTextInput(password)
        composeRule.onNodeWithTag("login").performClick()
        try {
            composeRule.waitUntil(1000) {
                loggedInCount == 1
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(0, registerButtonClicks)
        assertEquals(0, loggedInCount)
    }

    @Test
    fun testToLoginWithoutPassword() {
        val email = "test@gmail.com"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("login").performClick()
        try {
            composeRule.waitUntil(1000) {
                loggedInCount == 1
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(0, registerButtonClicks)
        assertEquals(0, loggedInCount)
    }
    @Test
    fun testRegisterButton(){
        composeRule.onNodeWithTag("register").performClick()
        try {
            composeRule.waitUntil(1000) {
                registerButtonClicks == 1
            }
        } catch (_: ComposeTimeoutException) {
        }
        assertEquals(1, registerButtonClicks)
    }
}