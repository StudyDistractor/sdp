package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.distractionStat.DistractionStatModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class DistractionStatServiceFake : DistractionStatModel {
    private var likeCount : Int = 0
    private var dislikeCount : Int = 0
    private var feedbackList : List<String> = listOf("test feedback1")
    private var tags : List<String> = listOf("test tag1")
    override fun fetchDistractionFeedback(did: String): Task<List<String>> {
        return Tasks.forResult(feedbackList)
    }

    override fun fetchDistractionTags(did: String): Task<List<String>> {
        return Tasks.forResult(tags)
    }

    override fun fetchLikeCount(did: String): Task<Int> {
        return Tasks.forResult(likeCount)
    }

    override fun fetchDislikeCount(did: String): Task<Int> {
        return Tasks.forResult(dislikeCount)
    }

    override fun postNewFeedback(did: String, feedback: String): Task<Void> {
        feedbackList = feedbackList.plus(feedback)
        return Tasks.whenAll(setOf(Tasks.forResult {
            feedbackList
        }))
    }

    override fun postLike(did: String): Task<Void> {
        likeCount += 1
        return Tasks.whenAll(setOf(Tasks.forResult(likeCount)))
    }

    override fun postDislike(did: String): Task<Void> {
        dislikeCount += 1
        return Tasks.whenAll(setOf(Tasks.forResult(dislikeCount)))
    }

    override fun addTag(did: String, tag: String): Task<Void> {
        tags = tags.plus(tag)
        return Tasks.whenAll(setOf(Tasks.forResult(tags)))
    }

}