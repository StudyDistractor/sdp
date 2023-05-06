package com.github.studydistractor.sdp.eventList

import com.github.studydistractor.sdp.data.Event
import com.google.android.gms.tasks.Task

interface EventListModel {
    /**
     * Give the list of all available events in the database
     */
    fun getAllEvents(): List<Event>

    /**
     * Subscribe to the given eventId event participants.
     * Execute the listener each time the event changes.
     * @param eventId the id of the Event to subscribe to
     * @param successListener the listener to execute each time the data changes
     * @param failureListener the listener to execute each time there is an error
     */
    fun subscribeToEventParticipants(
        eventId: String,
        successListener: (Int) -> Unit,
        failureListener: (String) -> Unit = {}
    )
}