package com.github.studydistractor.sdp.eventHistory

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventClaimPoints
import com.github.studydistractor.sdp.data.EventParticipants
import com.github.studydistractor.sdp.data.UserData
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
     * Observe the updates of the eventClaimPoints and call the given function on the values
     * @param onEventClaimPointsChange the function to call
     */
    fun observeEventClaimPoints(onEventClaimPointsChange: (List<EventClaimPoints>) -> Unit)

    /**
     * Observe the updates of the currentUser and call the given function on the values
     * @param onCurrentUserChange the function to call
     */
    fun observeCurrentUser(onCurrentUserChange: (UserData) -> Unit)

    /**
     * Upload a given user to the database
     * @param userData the user to upload
     */
    fun postUser(userData: UserData): Task<Void>

    /**
     * Upload an event claim points to the database
     * @param eventClaimPoints the event claim post to upload
     */
    fun postEventClaimPoints(eventClaimPoints: EventClaimPoints): Task<Void>
}