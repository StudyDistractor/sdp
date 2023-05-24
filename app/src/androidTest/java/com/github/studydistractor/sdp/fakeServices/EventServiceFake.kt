package com.github.studydistractor.sdp.fakeServices

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.event.EventModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class EventServiceFake: EventModel {
    val events = mapOf(
        "event0" to
                Event(
                    eventId = "event0",
                    name = "event0-name",
                    description = "event0-description",
                    start = "01-01-1970 00:00",
                    end = "01-01-9999 00:00",
                    lateParticipation = true
                ),
        "event1" to
                Event(
                    eventId = "event1",
                    name = "event1-name",
                    description = "event1-description",
                    start = "01-01-1970 00:00",
                    end = "01-01-9999 00:00",
                    lateParticipation = true
                ),
        "event2" to
                Event(
                    eventId = "event2",
                    name = "event2-name",
                    description = "event2-description",
                    start = "01-01-1970 00:00",
                    end = "01-01-9999 00:00",
                ),
    )

    var eventParticipants: MutableMap<String, MutableMap<String, Boolean>> = mutableMapOf(
        "event0" to mutableMapOf(
            "user0" to false,
            "user1" to false
        ),
        "event1" to mutableMapOf(
            "user0" to true
        )
    )

    var eventSuccessListener: (Event) -> Unit = {}
    var eventParticipantsSuccessListener: (List<String>) -> Unit = {}
    var userIdSuccessListener: (String) -> Unit = {}
    var currentEventId: String = "event0"
    var currentUserId = "user0"

    override fun subscribeToUserId(
        successListener: (String) -> Unit,
        failureListener: (String) -> Unit
    ) {
        successListener(currentUserId)
    }

    override fun isParticipating(eventId: String): Boolean {
         return eventParticipants[eventId]?.let {
                it[currentUserId] ?: false
            } ?: false
    }


    override fun addParticipant(eventId: String, userId: String): Task<Void> {
        if (currentUserId.isEmpty()) return Tasks.forException(Exception("User not logged in"))
        val mutableMap = eventParticipants[eventId]!!
        mutableMap[userId] = true
        eventParticipants[eventId] = mutableMap
        Log.d("EventServiceFake", "addParticipant: ${eventParticipants[eventId]}")
        Log.d("FilterParticipant", "addParticipant: ${eventParticipants[eventId]!!.filterValues { it }}")
        return Tasks.forResult(null)
    }

    override fun removeParticipant(eventId: String, userId: String): Task<Void> {
        if (currentUserId.isEmpty()) return Tasks.forException(Exception("User not logged in"))
        eventParticipants[eventId]!![userId] = false
        Log.d("EventServiceFake", "removeParticipant: ${eventParticipants[eventId]}")

        return Tasks.forResult(null)
    }

    override fun changeParticipantListener(eventId: String) {
        currentEventId = eventId
    }

    override fun getParticipants(): SnapshotStateList<String> {
        val list = eventParticipants[currentEventId]!!.filterValues { it }.keys.toMutableStateList()

        list.forEach() {
            Log.d("EventServiceFake", "getParticipants: $it")
        }
        return list
    }

}