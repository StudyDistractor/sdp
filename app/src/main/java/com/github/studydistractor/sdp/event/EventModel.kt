package com.github.studydistractor.sdp.event

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Event
import com.google.android.gms.tasks.Task

/**
 * Handle storage and retrieval of events for EventScreen
 */
interface EventModel {
    /**
     * Subscribe to the given eventId.
     * Execute the listener each time the event changes.
     * @param eventId the id of the Event to subscribe to
     * @param successListener the listener to execute each time the data changes
     * @param failureListener the listener to execute each time there is an error
     */
    fun subscribeToEvent(
        eventId: String,
        successListener: (Event) -> Unit,
        failureListener: (String) -> Unit = {}
    )
    /**
     * Subscribe to the given eventId event participants.
     * Execute the listener each time the event changes.
     * @param eventId the id of the Event to subscribe to
     * @param successListener the listener to execute each time the data changes
     * @param failureListener the listener to execute each time there is an error
     */
    fun subscribeToEventParticipants(
        eventId: String,
        successListener: (List<String>) -> Unit,
        failureListener: (String) -> Unit = {}
    )

    /**
     * Remove all the previous subscriptions to event participants
     */
    fun unsubscribeFromAllEventParticipants()

    /**
     * Remove all the previous subscriptions to events
     */
    fun unsubscribeFromAllEvents()

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
     * Get the event data for the given eventID.
     * @param eventId the id of the Event
     */
    fun getEvent(eventId: String): Task<Event>

    /**
     * Return true iff the user is participating to the event.
     * @param eventId the id of the event
     * @param userId the id of the user
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