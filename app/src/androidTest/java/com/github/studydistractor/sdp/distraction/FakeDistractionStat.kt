package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.runtime.mutableStateListOf
import com.github.studydistractor.sdp.distractionStat.DistractionStatModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class FakeDistractionStat : DistractionStatModel {
    var feedbacks = mutableStateListOf("Super", "Top")
    var tags = mutableStateListOf("Games", "Music")
    var likes = 0
    var dislike = 0
    override fun fetchDistractionFeedback(did: String): Task<List<String>> {
        return Tasks.forResult(feedbacks)
    }

    override fun fetchDistractionTags(did: String): Task<List<String>> {
        return Tasks.forResult(tags)
    }

    override fun fetchLikeCount(did: String): Task<Int> {
        return Tasks.forResult(likes)
    }

    override fun fetchDislikeCount(did: String): Task<Int> {
        return Tasks.forResult(dislike)
    }

    override fun postNewFeedback(did: String, feedback: String) : Task<Void> {
        feedbacks.add(feedback)
        return Tasks.whenAll(setOf(Tasks.forResult("")))
    }

    override fun postLike(did: String) : Task<Void>{
        likes += 1
        return Tasks.whenAll(setOf(Tasks.forResult("")))
    }

    override fun postDislike(did: String) : Task<Void>{
        dislike += 1
        return Tasks.whenAll(setOf(Tasks.forResult("")))
    }

    override fun addTag(did: String, tag: String): Task<Void> {
        tags.add(tag)
        return Tasks.whenAll(setOf(Tasks.forResult("")))
    }

    override fun removeTag(did: String, tag: String) : Task<Void>{
        tags.remove(tag)
        return Tasks.whenAll(setOf(Tasks.forResult("")))
    }
}