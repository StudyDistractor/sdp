package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.UserData
import com.github.studydistractor.sdp.eventHistory.EventHistoryModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class EventHistoryServiceFake: EventHistoryModel {

    val events = mutableListOf(
        Event(
            "event1",
            "event1",
            "event1",
            1.0,
            1.0,
            "10-10-1997 00:00",
            "11-11-1997 00:00",
            false,
            1,
            "chatId1"
        ),
        Event(
            "event2",
            "event2",
            "event2",
            2.0,
            2.0,
            "10-10-1998 00:00",
            "11-11-1998 00:00",
            false,
            2,
            "chatId2"
        ),
        Event(
            "event3",
            "event3",
            "event3",
            3.0,
            3.0,
            "10-10-1999 00:00",
            "11-11-1999 00:00",
            false,
            3,
            "chatId3"
        ),
        Event(
            "NotFinishedEvent",
            "NotFinishedEvent",
            "NotFinishedEvent",
            3.0,
            3.0,
            "10-10-1999 00:00",
            "10-10-2050 00:00",
            false,
            4,
            "NotFinishedEvent"
        ),
        Event(
            "UserHasNotTakenPartIn",
            "UserHasNotTakenPartIn",
            "UserHasNotTakenPartIn",
            3.0,
            3.0,
            "10-10-1999 00:00",
            "10-10-2000 00:00",
            false,
            5,
            "UserHasNotTakenPartIn"
        )
    )

    val eventParticipants = mutableMapOf(
        "event1" to
            mutableListOf("userId", "Joe", "Billy"),
        "event2" to
            mutableListOf("userId", "Joe", "Max", "Billy"),
        "event3" to
            mutableListOf("userId", "Daniel"),
        "NotFinishedEvent" to
            mutableListOf("userId", "Kevin", "Nelson"),
        "UserHasNotTakenPartIn" to
            mutableListOf("Athena", "Adrien", "Yohan", "Willy", "Orélian"),
    )

    val claimedPoints = mutableMapOf(
        "event1" to mutableListOf("Joe", "Billy"),
        "event2" to mutableListOf("userId", "Joe", "Billy")
    )

    var currentUser =
        UserData(
            "userId",
            "userId",
            "userId",
            "+41000000000",
        12,
            0
        )

    override fun observeHistory(userId: String, onHistoryChange: (List<Event>) -> Unit) {
        onHistoryChange(events.toList())
    }

    override fun claimPoints(userId: String, eventId: String): Task<Void> {
        return if(!claimedPoints.containsKey(eventId)) {
            claimedPoints[eventId] = mutableListOf(userId)
            Tasks.forResult(null)
        } else if(!claimedPoints[eventId]!!.contains(userId)) {
            claimedPoints[eventId]!!.add(userId)
            Tasks.forResult(null)
        } else {
            Tasks.forException(Exception("Couldn't add points"))
        }
    }

}