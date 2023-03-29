package com.github.studydistractor.sdp.login

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.login.LoginActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginActivityTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<LoginActivity>()

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun testToLoginWithValidEmailAndPassword() {
        var email = "test@gmail.com"
        var password = "1234567890"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("password").performTextInput(password)
        composeRule.onNodeWithTag("login").performClick()
    }

    @Test
    fun testToLoginWithInvalidEmailAndPassword() {
        var email = "email"
        var password = "password"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("password").performTextInput(password)
        composeRule.onNodeWithTag("login").performClick()
    }

    @Test
    fun testToLoginWithoutEmail() {
        var email = "test@gmail.com"
        var password = "1234567890"
        composeRule.onNodeWithTag("password").performTextInput(password)
        composeRule.onNodeWithTag("login").performClick()
    }

    @Test
    fun testToLoginWithoutPassword() {
        var email = "test@gmail.com"
        var password = "1234567890"
        composeRule.onNodeWithTag("email").performTextInput(email)
        composeRule.onNodeWithTag("login").performClick()
    }
    @Test
    fun testRegisterButton(){
        composeRule.onNodeWithTag("register").performClick()
    }
}