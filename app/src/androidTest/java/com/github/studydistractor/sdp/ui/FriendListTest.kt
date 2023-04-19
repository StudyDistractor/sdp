package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.account.FakeFriends
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FriendListTest {

    private lateinit var fakeUser : FakeFriends

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        fakeUser = FakeFriends()
        rule.inject()
        composeTestRule.setContent {
            FriendListScreen(user = fakeUser)
        }
    }
    @Test
    fun testTextField(){
        composeTestRule.onNodeWithTag("friend-list-screen__friend-text-field").assertIsDisplayed()
    }
    @Test
    fun testAddButton(){
        composeTestRule.onNodeWithTag("friend-list-screen__friend-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("friend-list-screen__friend-text-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag("friend-list-screen__friend-text-field").performTextInput("newFriend")
        composeTestRule.onNodeWithTag("friend-list-screen__friend-button").performClick()
        assertEquals(3, fakeUser.fetchAllFriends(fakeUser.getCurrentUid()).size)
    }
    @Test
    fun testFriendList(){
        for (f in fakeUser.friendlist){
            composeTestRule.onNodeWithTag("friend-list-screen__friend-$f").assertIsDisplayed()
            composeTestRule.onNodeWithTag("friend-list-screen__delete-$f").assertIsDisplayed()
        }
        for (f in fakeUser.friendlist){
            composeTestRule.onNodeWithTag("friend-list-screen__delete-$f").performClick()
        }
    }
}