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
 * @property chatId id of the chat that is linked to the event
 */
data class Event(
    val eventId: String?,
    val name: String,
    val description: String,
    val lat: Double,
    val long: Double,
    val start: String,
    val end: String,
    val lateParticipation: Boolean,
    val numberOfPoints: Int,
    val chatId: String)

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
            eventId, name!!, description!!, lat!!, long!!, start!!, end!!, lateParticipation, numberOfPoints!!, chatId!!
        )
    }
}

/**
 * represent participants of a particular events
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
 * @param chatId id of the chat
 * @param messageIds list of messageId that were sent in the chat
 */
data class FirebaseEventParticipants(
    val eventId: String?,
    val participants: List<String>? = null
) {
    fun toEventParticipants(): EventParticipants {
        return EventParticipants(
            eventId, participants!!
        )
    }
}