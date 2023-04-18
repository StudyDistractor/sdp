package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

interface DistractionStatInterface {
    /**
     * Fetch feedback of the did Distraction a list of feedback of user.
     * @param did The did of the Distraction
     */
    fun fetchDistractionFeedback(did: String) : SnapshotStateList<String>

    /**
     * Fetch the tags of the did Distraction a list of feedback of user.
     * @param did The did of the Distraction
     */
    fun fetchDistractionTags(did : String) : SnapshotStateList<String>

    /**
     * Fetch the number of like of the did Distraction
     * @param did The did of the Distraction
     */
    fun fetchLikeCount(did : String) : MutableState<Int>

    /**
     * Fetch the number of like of the did Distraction
     * @param did The did of the Distraction
     */
    fun fetchDislikeCount(did : String) : MutableState<Int>

    /**
     * Post a new feedback from the uid User with the feedback string to the did Distraction
     * @param did The did of the Distraction
     * @param uid The uid of the User
     * @param feedback The comment of the feedback
     */
    fun postNewFeedback(did : String, uid : String, feedback : String)

    /**
     * Post a like of the given Distraction
     * @param did The did of the Distraction
     * @param uid The uid of the User
     */
    fun postLike(did : String, uid : String)

    /**
     * Post a like of the given Distraction
     * @param did The did of the Distraction
     * @param uid The uid of the User
     */
    fun postDislike(did : String, uid : String)


    /**
     * Add a tag to the Distraction
     * @param did The did of the Distraction
     * @param tag The tag to be added to the distraction
     */
    fun addTag(did : String, tag : String)

    /**
     * Remove the tag from the list of tag of the Distraction
     * @param did The did of the Distraction
     * @param tag The tag to be removed the distraction
     */
    fun removeTag(did : String, tag : String)
}