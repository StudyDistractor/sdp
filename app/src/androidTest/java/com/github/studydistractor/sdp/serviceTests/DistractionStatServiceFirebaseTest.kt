package com.github.studydistractor.sdp.serviceTests

import com.github.studydistractor.sdp.distractionStat.DistractionStatServiceFirebase
import kotlinx.coroutines.tasks.await
import org.junit.Assert.assertThrows
import org.junit.Test

class DistractionStatServiceFirebaseTest {

    val service = DistractionStatServiceFirebase("TestFeedback", "TestLikes", "TestDislikes", "TestTagsUsers")

    @Test
    fun testGetFeedback() {
        val feedback = service.fetchDistractionFeedback("TestActivity")
        Thread.sleep(500)
        assert(feedback.result.isNotEmpty())
    }

    @Test
    fun testGetTags() {
        val tags = service.fetchDistractionTags("testActivity")
        Thread.sleep(500)
        assert(tags.result.isNotEmpty())
    }

    @Test
    fun testGetLikeCount() {
        val likes = service.fetchLikeCount("testActivity")
        Thread.sleep(500)
        assert(likes.result == 1)
    }

    @Test
    fun testGetDislikeCount() {
        val dislikes = service.fetchDislikeCount("testActivity")
        Thread.sleep(500)
        assert(dislikes.result == 1)
    }

    @Test
    fun postFeedbackThrowsWithEmptyFeedback() {
        assertThrows(IllegalArgumentException::class.java) {
            service.postNewFeedback("testActivity", "")
        }
    }

    @Test
    fun addTagAddsTag() {
        val random = (0..1000).random()
        val activityName = "testActivity$random"
        val newTags = service.addTag(activityName, "testTag").continueWith {
            service.fetchDistractionTags(
                activityName
            )
        }
        Thread.sleep(500)
        assert(newTags.result.result.contains("testTag"))
    }

}