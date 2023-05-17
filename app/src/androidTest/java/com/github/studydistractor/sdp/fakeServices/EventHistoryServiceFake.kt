package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventParticipants
import com.github.studydistractor.sdp.eventHistory.EventHistoryModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class EventHistoryServiceFake: EventHistoryModel {
    private fun getEvents(): Task<List<Event>> {
        return Tasks.forResult(
            listOf(
                Event(
                    "event1",
                    "event1",
                    "event1",
                    1.0,
                    1.0,
                    "10-10-1997 00:00",
                    "11-11-1997 00:00",
                    false,
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
                    "UserHasNotTakenPartIn"
                )
            )
        )
    }

    private fun getEventParticipants(): Task<List<EventParticipants>> {
        return Tasks.forResult(
            listOf(
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
        )
    }

    override fun observeEvents(onEventChange: (List<Event>) -> Unit) {
        getEvents().continueWith {
            t -> onEventChange(t.result)
        }
    }

    override fun observeEventsParticipants(onEventParticipantsChange: (List<EventParticipants>) -> Unit) {
        getEventParticipants().continueWith {
                t -> onEventParticipantsChange(t.result)
        }
    }

    override fun getCurrentUid(): Task<String> {
        return Tasks.forResult("userId")
    }
}