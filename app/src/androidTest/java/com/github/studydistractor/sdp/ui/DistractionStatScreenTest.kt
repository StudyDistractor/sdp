package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.github.studydistractor.sdp.procrastinationActivity.FakeDistractionStat
import com.github.studydistractor.sdp.procrastinationActivity.FirebaseDistractionStat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DistractionStatScreenTest {
    val FakeDistractionStat = FakeDistractionStat()

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()

        composeTestRule.setContent {
            DistractionStatScreen("uid", "did", FakeDistractionStat)
        }
    }
    @Test
    fun testButtonLike(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__like").assertIsDisplayed()
    }
    @Test
    fun testButtonDislike(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__dislike").assertIsDisplayed()
    }
    @Test
    fun testTagTextField(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-text-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__tag-button").assertIsDisplayed()
    }
    @Test
    fun testFeedbackTextField(){
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-text-field").assertIsDisplayed()
        composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-button").assertIsDisplayed()
    }
    @Test
    fun testFeedbacks(){
        for(i in FakeDistractionStat.feedbacks){
            composeTestRule.onNodeWithTag("distraction-stat-screen__feedback-$i").assertIsDisplayed()
        }
    }
    @Test
    fun testTags(){
        for(i in FakeDistractionStat.tags){
            composeTestRule.onNodeWithTag("distraction-stat-screen__tag-${i}").assertIsDisplayed()
        }
    }
    @Test
    fun testEmptyAddTag(){

        val distractionStat = FirebaseDistractionStat("did")
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.addTag("", "tag")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.addTag("did", "")
        }
    }
    @Test
    fun testEmptyAddFeedback(){
        val distractionStat = FirebaseDistractionStat("did")
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.postNewFeedback("did", "uid", "")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.postNewFeedback("", "uid", "feedback")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.postNewFeedback("did", "", "feedback")
        }
    }
    @Test
    fun testEmptyFetch(){
        val distractionStat = FirebaseDistractionStat("did")
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.fetchDistractionFeedback("")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.fetchDistractionTags("")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.fetchDislikeCount("")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.fetchLikeCount("")
        }
    }
    @Test
    fun testEmptyPost(){
        val distractionStat = FirebaseDistractionStat("did")
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.postDislike("did", "")
            distractionStat.postDislike("", "uid")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.postLike("did", "")
            distractionStat.postLike("", "uid")
        }
        assertThrows(IllegalArgumentException::class.java) {
            distractionStat.removeTag("did", "")
            distractionStat.removeTag("", "uid")
        }
    }
}