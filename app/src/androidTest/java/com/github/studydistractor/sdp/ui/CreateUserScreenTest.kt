package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.github.studydistractor.sdp.user.FakeCreateUserModule.provideFakeCreateUserModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class CreateUserScreenTest {

    private var accountCreated = false
    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()
        composeRule.setContent {
            CreateUserScreen(
                onUserCreated = { accountCreated = true },
                userService = provideFakeCreateUserModule()
            )
        }
    }

    //    works
    @Test
    fun createAccountScreenExists() {
        composeRule.onNodeWithTag("create_account_screen").assertExists()
    }
    @Test
    fun testTextNode() {
        val firstName = "John"
        val lastName = "Doe"
        val phone = "0000000000"

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
    fun firstNameFieldHasHint() {
        composeRule.onNodeWithTag("firstname").assert(hasText("First name"))
    }

    @Test
    fun lastNameFieldHasHint() {
        composeRule.onNodeWithTag("lastname").assert(hasText("Last name"))
    }

    @Test
    fun phoneFieldHasHint() {
        composeRule.onNodeWithTag("phone").assert(hasText("Phone (ex: +41123456789)"))
    }

    @Test
    fun birthdayFieldHasHint() {
        composeRule.onNodeWithTag("birthday").assert(hasText("Birthday"))
    }

    @Test
    fun performTextInputOnBirthdayField() {
        val birthday = "01/01/2000"
        composeRule.onNodeWithTag("birthday").performTextInput(birthday)
        composeRule.onNodeWithTag("birthday").assert(hasText(birthday))
    }

    @Test
    fun selectBirthdayButtonExistsAndIsClickable() {
        composeRule.onNodeWithTag("selectBirthdayButton").assertExists()
        composeRule.onNodeWithTag("selectBirthdayButton").assert(hasText("Select"))
        composeRule.onNodeWithTag("selectBirthdayButton").assertHasClickAction()
    }




    @Test
    fun testValidButton(){
        composeRule.onNodeWithTag("validbutton").assertExists()
        composeRule.onNodeWithTag("validbutton").assertIsDisplayed()
        composeRule.onNodeWithTag("validbutton").assertHasClickAction()
        composeRule.onNodeWithTag("validbutton").assert(hasText("Validate"))
    }


    @Test
    fun testValidButtonWithoutPhone(){
        val firstName = "John"
        val lastName = "Doe"

        composeRule.onNodeWithTag("firstname").performTextInput(firstName)
        composeRule.onNodeWithTag("lastname").performTextInput(lastName)
        assertEquals(false, accountCreated)
        composeRule.onNodeWithTag("validbutton").performClick()
        assertEquals(false, accountCreated)
    }


    @Test
    fun createAccountWorksWithValidData() {
        val firstName = "John"
        val lastName = "Doe"
        val phone = "0000000000"
        val birthday = "01/01/2000"

        composeRule.onNodeWithTag("firstname").performTextInput(firstName)
        composeRule.onNodeWithTag("lastname").performTextInput(lastName)
        composeRule.onNodeWithTag("phone").performTextInput(phone)
        composeRule.onNodeWithTag("birthday").performTextInput(birthday)
        assertEquals(false, accountCreated)
        composeRule.onNodeWithTag("validbutton").performClick()
        try {
            composeRule.waitUntil (1000) {
                accountCreated
            }
        } catch (_: Exception) {
        }
        assertEquals(true, accountCreated)
    }
}