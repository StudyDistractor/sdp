package com.github.studydistractor.sdp.data

/**
 * Represent an event, an event is a group distraction that can only be done during a specific
 * time window
 *
 * @property eventId id of the event
 * @property name name of the event
 * @property description description of the event
 * @property lat latitude position of the event
 * @property long longitude position of the event
 * @property start time when the event starts
 * @property end time when the event ends
 * @property lateParticipation enable late participation (Can users join in the middle of the event)
 * @property numberOfPoints number of points awarded to the user when they participate in the event
 * @property chatId id of the chat that is linked to the event
 */
data class Event(
    val eventId: String,
    val name: String,
    val description: String,
    val lat: Double,
    val long: Double,
    val start: String,
    val end: String,
    val lateParticipation: Boolean,
    val numberOfPoints: Int,
    val chatId: String?)

/**
 * Represent an event that is received or sent from/to the firebase database
 *
 * @property eventId id of the event
 * @property name name of the event
 * @property description description of the event
 * @property lat latitude position of the event
 * @property long longitude position of the event
 * @property start time when the event starts
 * @property end time when the event ends
 * @property lateParticipation enable late participation (Can users join in the middle of the event)
 * @property chatId id of the chat that is linked to the event
 */
data class FirebaseEvent(
    val eventId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val lat: Double? = null,
    val long: Double? = null,
    val start: String? = null,
    val end: String? = null,
    val lateParticipation: Boolean = false,
    val numberOfPoints: Int? = 0,
    val chatId: String? = null) {
    fun toEvent(): Event {
        return Event(
            eventId!!, name!!, description!!, lat!!, long!!, start!!, end!!, lateParticipation, numberOfPoints!!, chatId!!
        )
    }
}

/**
 * Represent participants of a particular events
 *
 * @property eventId id of the event
 * @property participants list of userId that participate in this event
 */
data class EventParticipants(
    val eventId: String? = null,
    val participants: List<String>? = null
)

/**
 * Represent a chat that is received or sent from/to the firebase database
 *
 * @property eventId id of the chat
 * @property participants list of messageId that were sent in the chat
 */
data class FirebaseEventParticipants(
    val eventId: String? = null,
    val participants: List<String>? = null
) {
    fun toEventParticipants(): EventParticipants {
        return EventParticipants(
            eventId, participants!!
        )
    }
}

/**
 * Represent the point to be claimed/claimed of a particular event
 *
 * @param eventId id of the chat
 * @param claimUser list of users that have already claimed their points
 */
data class EventClaimPoints(
    val eventId: String = "",
    val claimUser: List<String> = listOf()
)

/**
 * Represent the eventClaimPoint that is received or sent from/to the firebase database
 */
data class FirebaseEventClaimPoints(
    val eventId: String? = null,
    val claimUser: List<String>? = null
) {
    fun toEventClaimPoints(): EventClaimPoints{
        return EventClaimPoints(
            eventId!!, claimUser!!
        )
    }
}