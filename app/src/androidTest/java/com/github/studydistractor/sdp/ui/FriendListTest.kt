package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.fakeServices.FriendsServiceFake
import com.github.studydistractor.sdp.friends.FriendsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FriendListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeFriends = FriendsServiceFake()

    @Before
    fun setup() {
        composeTestRule.setContent {
            FriendsScreen(
                friendsViewModel = FriendsViewModel(fakeFriends)
            )
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
        for (f in fakeFriends.friendlist){
            composeTestRule.onNodeWithTag("friend-list-screen__friend-$f").assertIsDisplayed()
            composeTestRule.onNodeWithTag("friend-list-screen__history-$f").assertIsDisplayed()
            composeTestRule.onNodeWithTag("friend-list-screen__delete-$f").assertIsDisplayed()
        }
        for (f in fakeUser.friendlist){
            composeTestRule.onNodeWithTag("friend-list-screen__delete-$f").performClick()
        }
    }
    @Test
    fun testHistoryEmpty(){
        composeTestRule.onNodeWithTag("friend-list-screen__history-title").assertDoesNotExist()
    }
    @Test
    fun testHistoryIsDisplayed(){
        composeTestRule.onNodeWithTag(
            "friend-list-screen__history-${fakeUser.friendlist[0]}")
            .performClick()
        composeTestRule.onNodeWithTag("friend-list-screen__history-title").assertIsDisplayed()
    }
}