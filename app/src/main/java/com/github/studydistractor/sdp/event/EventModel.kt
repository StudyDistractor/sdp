package com.github.studydistractor.sdp.event

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Event
import com.google.android.gms.tasks.Task

/**
 * Handle storage and retrieval of events for EventScreen
 */
interface EventModel {

    /**
     * Subscribe to userId changes.
     * Execute the listener each time the user changes.
     * @param successListener the listener to execute each time the data changes
     * @param failureListener the listener to execute each time there is an error
     */
    fun subscribeToUserId(
        successListener: (String) -> Unit,
        failureListener: (String) -> Unit = {}
    )

    /**
     * Return true iff the user is participating to the event.
     * @param eventId the id of the event
     */
    fun isParticipating(eventId: String): Boolean

    /**
     * Add the user as participant to the selected event.
     * @param eventId the id of the event
     * @param userId the id of the user
     */
    fun addParticipant(eventId: String, userId: String): Task<Void>

    /**
     * Remove the user as participant to the selected event.
     * @param eventId the id of the event
     * @param userId the id of the user
     */
    fun removeParticipant(eventId: String, userId: String): Task<Void>

    fun changeParticipantListener(eventId: String)

    fun getParticipants(): SnapshotStateList<String>
}