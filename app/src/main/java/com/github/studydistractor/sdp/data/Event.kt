package com.github.studydistractor.sdp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val eventId: String? = null,
    val name: String = "",
    val description: String = "",
    val lat: Double = .0,
    val long: Double = .0,
    val start: String = "",
    val end: String = "",
    val lateParticipation: Boolean = false,
    val numberOfPoints: Int = 0,
    val chatId: String? = null
){
    fun toEventEntity() : EventEntity{
        return EventEntity(
            0,
            eventId!!,
            name,
            description,
            lat,
            long,
            start,
            end,
            lateParticipation,
            numberOfPoints,
            chatId,
            0
        )
    }
}

/**
 * Represent an event, an event is a group distraction that can only be done during a specific
 * time window
 *
 * @property key the unique key of the event in room
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
@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    var key : Int = 0,
    var eventId: String? = null,
    var name: String = "",
    var description: String = "",
    var lat: Double = .0,
    var long: Double = .0,
    var start: String = "",
    var end: String = "",
    var lateParticipation: Boolean = false,
    var numberOfPoints: Int = 0,
    var chatId: String? = null,
    var history : Int = 0
) {
    fun toEvent(): Event {
        return Event(
            eventId!!,
            name,
            description,
            lat,
            long,
            start,
            end,
            lateParticipation,
            numberOfPoints,
            chatId
        )
    }
}

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
 * @property numberOfPoints number of points
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
    val lateParticipation: Boolean? = null,
    val numberOfPoints: Int? = null,
    val chatId: String? = null) {
    fun toEvent(): Event {
        return Event(
            eventId!!,
            name!!,
            description!!,
            lat!!,
            long!!,
            start!!,
            end!!,
            lateParticipation!!,
            numberOfPoints!!,
            chatId!!
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
    val participants: Map<String, Boolean> = mapOf()
)



/**
 * Represent the point to be claimed/claimed of a particular event
 *
 * @param claimUser list of users that have already claimed their points
 */
data class EventClaimPoints(
    val claimUser: Map<String, Boolean> = mapOf()
)

