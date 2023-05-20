package com.github.studydistractor.sdp.fakeServices
//
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.compose.runtime.toMutableStateList
//import com.github.studydistractor.sdp.data.Event
//import com.github.studydistractor.sdp.event.EventModel
//import com.google.android.gms.tasks.Task
//import com.google.android.gms.tasks.Tasks
//
//class EventServiceFake: EventModel {
//    val events = mapOf(
//        "event0" to
//        Event(
//            eventId = "event0",
//            name = "event0-name",
//            description = "event0-description",
//            start = "01-01-1970 00:00",
//            end = "01-01-9999 00:00",
//            lateParticipation = true
//        ),
//        "event1" to
//        Event(
//            eventId = "event1",
//            name = "event1-name",
//            description = "event1-description",
//            start = "01-01-1970 00:00",
//            end = "01-01-9999 00:00",
//            lateParticipation = true
//        ),
//        "event2" to
//        Event(
//            eventId = "event2",
//            name = "event2-name",
//            description = "event2-description",
//            start = "01-01-1970 00:00",
//            end = "01-01-9999 00:00",
//        ),
//    )
//
//    var currentEventId = ""
//    val currentUserId = "user0"
//
//    val eventParticipants: Map<String, MutableMap<String, Boolean>> = mapOf(
//        "event0" to mutableMapOf(
//            "user0" to false,
//            "user1" to false
//        ),
//        "event1" to mutableMapOf(
//            "user0" to true
//        )
//    )
//
//    var eventSuccessListener: (Event) -> Unit = {}
//    var eventParticipantsSuccessListener: (List<String>) -> Unit = {}
//    var userIdSuccessListener: (String) -> Unit = {}
//
//    override fun subscribeToEvent(
//        eventId: String,
//        successListener: (Event) -> Unit,
//        failureListener: (String) -> Unit
//    ) {
//        eventSuccessListener = successListener
//    }
//
//    override fun subscribeToEventParticipants(
//        eventId: String,
//        successListener: (List<String>) -> Unit,
//        failureListener: (String) -> Unit
//    ) {
//        eventParticipantsSuccessListener = successListener
//    }
//
//    override fun unsubscribeFromAllEventParticipants() {
//        eventSuccessListener = {}
//    }
//
//    override fun unsubscribeFromAllEvents() {
//        eventParticipantsSuccessListener = {}
//    }
//
//    override fun subscribeToUserId(
//        successListener: (String) -> Unit,
//        failureListener: (String) -> Unit
//    ) {
//        userIdSuccessListener = successListener
//    }
//
//    override fun getEvent(eventId: String): Task<Event> {
//        return if(events.containsKey(eventId)) {
//            Tasks.forResult(events[eventId])
//        } else {
//            Tasks.forException(Exception("No such event."))
//        }
//    }
//
//    override fun isParticipating(eventId: String): Boolean {
//        return eventParticipants[eventId]?.get(currentUserId)!!
//    }
//
//    fun isParticipating(eventId: String, userId: String): Task<Boolean> {
//        return Tasks.forResult(
//            eventParticipants.containsKey(eventId)
//                    && eventParticipants[eventId] != null
//                    && eventParticipants[eventId]!!.containsKey(userId)
//                    && eventParticipants[eventId]!![userId] != null
//                    && eventParticipants[eventId]!![userId]!!
//        )
//    }
//
//    override fun addParticipant(eventId: String, userId: String): Task<Void> {
//        eventParticipants[eventId]!![userId] = true
//        triggerParticipantsChange(eventParticipants[eventId]!!.filterValues { it }.keys.toList())
//        return Tasks.forResult(null)
//    }
//
//    override fun removeParticipant(eventId: String, userId: String): Task<Void> {
//        eventParticipants[eventId]!![userId] = false
//        triggerParticipantsChange(eventParticipants[eventId]!!.filterValues { it }.keys.toList())
//        return Tasks.forResult(null)
//    }
//
//    override fun changeParticipantListener(eventId: String) {
//        currentEventId = eventId
//    }
//
//    override fun getParticipants(): SnapshotStateList<String> {
//        return eventParticipants[currentEventId]?.filter { (key, value) -> value }!!.keys.toMutableStateList()
//    }
//
//    fun triggerParticipantsChange(participants: List<String>) {
//        eventParticipantsSuccessListener(participants)
//    }
//
//    fun triggerUserIdChange(uid: String) {
//        userIdSuccessListener(uid)
//    }
//
//    fun triggerEventChange(event: Event) {
//        eventSuccessListener(event)
//    }
//}