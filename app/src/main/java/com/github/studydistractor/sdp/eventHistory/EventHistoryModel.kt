package com.github.studydistractor.sdp.eventHistory

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventParticipants
import com.google.android.gms.tasks.Task

interface EventHistoryModel {

    /**
     * Observe the updates of the events and call the given function on the values
     * @param onEventChange the function to call
     */
    fun observeEvents(onEventChange: (List<Event>) -> Unit)

    /**
     * Observe the updates of the eventParticipants and call the given function on the values
     * @param onEventParticipantsChange the function to call
     */
    fun observeEventsParticipants(onEventParticipantsChange: (List<EventParticipants>) -> Unit)

    /**
     * Get the id of the current user
     * @return a task containing the id if successful
     */
    fun getCurrentUid() : Task<String>
}