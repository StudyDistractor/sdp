package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class FakeDistractionStat : DistractionStatInterface {
    var feedbacks = mutableStateListOf("Super", "Top")
    var tags = mutableStateListOf("Games", "Music")
    var likes = mutableStateOf(0)
    var dislike = mutableStateOf(0)
    override fun fetchDistractionFeedback(did: String): SnapshotStateList<String> {
        return feedbacks
    }

    override fun fetchDistractionTags(did: String): SnapshotStateList<String> {
        return tags
    }

    override fun fetchLikeCount(did: String): MutableState<Int> {
        return likes
    }

    override fun fetchDislikeCount(did: String): MutableState<Int> {
        return dislike
    }

    override fun postNewFeedback(did: String, uid: String, feedback: String) {
        feedbacks.add(feedback)
    }

    override fun postLike(did: String, uid: String) {
        likes.value += 1
    }

    override fun postDislike(did: String, uid: String) {
        dislike.value += 1
    }

    override fun addTag(did: String, tag: String) {
        tags.add(tag)
    }

    override fun removeTag(did: String, tag: String) {
        tags.remove(tag)
    }
}