package com.github.studydistractor.sdp.eventHistory

import com.github.studydistractor.sdp.data.Event
import com.google.android.gms.tasks.Task

interface EventHistoryModel {
    /**
     * Observer the history of the given user
     * Remove the previous observer if any.
     * @param userId the user
     * @param onHistoryChange the function to call
     */
    fun observeHistory(userId: String, onHistoryChange: (List<Event>) -> Unit)

    /**
     * Claim points for a given user to a given event
     * @param userId the user
     * @param eventId the event
     */
    fun claimPoints(userId: String, eventId: String): Task<Void>
}