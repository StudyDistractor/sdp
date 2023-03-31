package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.account.CreateAccountModule.provideCreateAccount
import com.github.studydistractor.sdp.account.FakeCreateAccModule.provideFakeCreateModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CreateAccountScreenTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule() // If you are doing UI testing, but now the Activity is injected with the mock !

    @Before
    fun setup() {
        rule.inject()
        composeRule.setContent {
            CreateAccountScreen(
                {},
                createAccount = provideFakeCreateModule()
            )
        }
    }

    @Test
    fun testToCreateAccountWithInvalidDate() {
        var firstName = "John"
        var lastName = "Doe"
        var phone = "0000000000"

        composeRule.onNodeWithTag("firstname").performTextInput(firstName)
        composeRule.onNodeWithTag("lastname").performTextInput(lastName)
        composeRule.onNodeWithTag("phone").performTextInput(phone)
        composeRule.onNodeWithTag("validbutton").performClick()

    }

    @Test
    fun testValidButton(){
        composeRule.onNodeWithTag("validbutton").performClick()
    }

    @Test
    fun testCalendarButton(){
        composeRule.onNodeWithTag("birthday").performClick()

    }

    @Test
    fun testValidButtonWithoutPhone(){
        var firstName = "John"
        var lastName = "Doe"

        composeRule.onNodeWithTag("birthday").performClick()
        composeRule.onNodeWithTag("firstname").performTextInput(firstName)
        composeRule.onNodeWithTag("lastname").performTextInput(lastName)
        composeRule.onNodeWithTag("validbutton").performClick()
    }

    @Test
    fun testCheckNameFormat(){
        var createAcc = provideCreateAccount()

        var name =""

        assertEquals(false, createAcc.checkNameFormat(name))
    }

}