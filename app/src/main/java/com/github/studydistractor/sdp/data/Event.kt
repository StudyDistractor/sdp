package com.github.studydistractor.sdp.data

/**
 * Represent an event, an event is a group distraction that can only be done during a specific
 * time window
 *
 * @property eventId id of the event
 * @property name name of the event
 * @property description description of the event
 * @property start time when the event starts
 * @property end time when the event ends
 * @property lateParticipation enable late participation (Can users join in the middle of the event)
 * @property chatId id of the chat that is linked to the event
 */
data class Event(
    val eventId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val start: String? = null,
    val end: String? = null,
    val lateParticipation: Boolean = false,
    val chatId: String? = null
)

/**
 * represent participants of a particular events
 *
 * @property eventId id of the event
 * @property participants list of participant in this event
 */
data class EventParticipants(
    val eventId: String? = null,
    val participants: List<userId> = listOf()
)