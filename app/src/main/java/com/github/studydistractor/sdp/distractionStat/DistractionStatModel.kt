package com.github.studydistractor.sdp.distractionStat

import com.google.android.gms.tasks.Task

/**
 * A interface that fetch the stats (feedback, like, dislike and tags)
 * And can add and remove like and dislike to a given Distraction
 */
interface DistractionStatModel {
    /**
     * Fetch feedback of the did Distraction a list of feedback of user.
     * @param did The did of the Distraction
     */
    fun fetchDistractionFeedback(did: String) : Task<List<String>>

    /**
     * Fetch the tags of the did Distraction a list of feedback of user.
     * @param did The did of the Distraction
     */
    fun fetchDistractionTags(did : String) : Task<List<String>>

    /**
     * Fetch the number of like of the did Distraction
     * @param did The did of the Distraction
     */
    fun fetchLikeCount(did : String) : Task<Int>

    /**
     * Fetch the number of like of the did Distraction
     * @param did The did of the Distraction
     */
    fun fetchDislikeCount(did : String) : Task<Int>

    /**
     * Post a new feedback from the uid User with the feedback string to the did Distraction
     * @param did The did of the Distraction
     * @param feedback The comment of the feedback
     */
    fun postNewFeedback(did : String, feedback : String) : Task<Void>

    /**
     * Post a like of the given Distraction
     * @param did The did of the Distraction
     */
    fun postLike(did : String) : Task<Void>

    /**
     * Post a like of the given Distraction
     * @param did The did of the Distraction
     */
    fun postDislike(did : String) : Task<Void>


    /**
     * Add a tag to the Distraction
     * @param did The did of the Distraction
     * @param tag The tag to be added to the distraction
     */
    fun addTag(did : String, tag : String) : Task<Void>

    /**
     * Remove the tag from the list of tag of the Distraction
     * @param did The did of the Distraction
     * @param tag The tag to be removed the distraction
     */
    fun removeTag(did : String, tag : String) : Task<Void>
}