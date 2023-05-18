package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventClaimPoints
import com.github.studydistractor.sdp.data.EventParticipants
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

    val eventParticipants = mutableListOf(
        EventParticipants(
            "event1",
            listOf("userId", "Joe", "Billy")
        ),
        EventParticipants(
            "event2",
            listOf("userId", "Joe", "Max", "Billy")
        ),
        EventParticipants(
            "event3",
            listOf("userId", "Daniel")
        ),
        EventParticipants(
            "NotFinishedEvent",
            listOf("userId", "Kevin", "Nelson")
        ),
        EventParticipants(
            "UserHasNotTakenPartIn",
            listOf("Athena", "Adrien", "Yohan", "Willy", "Orélian")
        )
    )

    val eventClaimPoints = mutableListOf(
        EventClaimPoints(
            "event1",
            listOf("Joe", "Billy")
        ),
        EventClaimPoints(
            "event2",
            listOf("userId", "Joe", "Billy")
        ),
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




    override fun observeEvents(onEventChange: (List<Event>) -> Unit) {
        onEventChange(events)
    }

    override fun observeEventsParticipants(onEventParticipantsChange: (List<EventParticipants>) -> Unit) {
        onEventParticipantsChange(eventParticipants)
    }

    override fun observeEventClaimPoints(onEventClaimPointsChange: (List<EventClaimPoints>) -> Unit) {
        onEventClaimPointsChange(eventClaimPoints)
    }

    override fun observeCurrentUser(onCurrentUserChange: (UserData) -> Unit) {
        onCurrentUserChange(currentUser)
    }

    override fun postUser(userData: UserData): Task<Void> {
        currentUser = userData
        return Tasks.forResult(null)
    }

    override fun postEventClaimPoints(eventClaimPoints: EventClaimPoints): Task<Void> {
        for (ecp in this.eventClaimPoints){
            if (ecp.eventId == eventClaimPoints.eventId){
                return Tasks.forResult(null)
            }
        }

        this.eventClaimPoints.add(eventClaimPoints)
        return Tasks.forResult(null)
    }

}