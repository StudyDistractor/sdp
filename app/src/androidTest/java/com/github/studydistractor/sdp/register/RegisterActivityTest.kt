package com.github.studydistractor.sdp.register

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
class RegisterActivityTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<RegisterActivity>()

    @Before
    fun setup() {
        rule.inject()
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

        composeTestRule.onNodeWithTag("register").performClick()
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

        composeTestRule.onNodeWithTag("register").performClick()
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

        composeTestRule.onNodeWithTag("register").performClick()
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

        composeTestRule.onNodeWithTag("register").performClick()
    }
}
