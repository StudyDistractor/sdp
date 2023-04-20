package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.login.FakeLoginAuth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@HiltAndroidTest
class LoginScreenTest {
    private var registerButtonClicks = 0
    private var loggedInCount = 0

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()

        registerButtonClicks = 0
        loggedInCount = 0
        composeRule.setContent {
            LoginScreen(
                onRegisterButtonClicked = { registerButtonClicks++ },
                onLoggedIn = { loggedInCount++ },
                loginAuth = FakeLoginAuth()
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
        val email = "validEmail"
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