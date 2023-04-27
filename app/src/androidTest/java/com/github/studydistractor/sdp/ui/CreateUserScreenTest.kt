package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.createUser.CreateUserViewModel
import com.github.studydistractor.sdp.fakeServices.CreateUserServiceFake
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CreateUserScreenTest {

    private var accountCreated = false

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        composeRule.setContent {
            CreateUserScreen(
                onUserCreated = { accountCreated = true },
                createUserViewModel = CreateUserViewModel(CreateUserServiceFake())
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