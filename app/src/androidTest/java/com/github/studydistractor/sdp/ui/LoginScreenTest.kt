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
    var registerButtonClicks = 0
    var loggedInCount = 0

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
    fun testToLoginWithValidEmailAndPassword() {
        var email = "test@gmail.com"
        var password = "1234567890"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("password").performTextInput(password)

        assertEquals(0, loggedInCount)
        composeRule.onNodeWithTag("login").performClick()
        composeRule.waitUntil(1000) {
            loggedInCount == 1
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
        var email = "email"
        var password = "password"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("password").performTextInput(password)
        composeRule.onNodeWithTag("login").performClick()

        assertEquals(0, registerButtonClicks)
        assertEquals(0, loggedInCount)
    }

    @Test
    fun testToLoginWithoutEmail() {
        var password = "1234567890"
        composeRule.onNodeWithTag("password").performTextInput(password)
        composeRule.onNodeWithTag("login").performClick()

        assertEquals(0, registerButtonClicks)
        assertEquals(0, loggedInCount)
    }

    @Test
    fun testToLoginWithoutPassword() {
        var email = "test@gmail.com"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("login").performClick()

        assertEquals(0, registerButtonClicks)
        assertEquals(0, loggedInCount)
    }
    @Test
    fun testRegisterButton(){
        composeRule.onNodeWithTag("register").performClick()
    }
}