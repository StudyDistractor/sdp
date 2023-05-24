package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.eventList.EventListModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventListServiceFake: EventListModel {

    private val time = Calendar.getInstance()
    private val format = SimpleDateFormat(
        "dd-MM-yyyy hh:mm",
        Locale.ENGLISH);

    var before: String
    var after: String
    init {
        time.add(Calendar.YEAR, -1)
        before = format.format(time.time)
        time.add(Calendar.YEAR, 2)
        after = format.format(time.time)
    }

    val dummy = Event(
        "id",
        "event name",
        "desciption",
        0.0,
        0.0,
        before,
        after,
        false,
        0,
        "chatId"
    )

    val dummy2 = Event(
        "id",
        "event name",
        "desciption",
        10.0,
        10.0,
        before,
        after,
        true,
        0,
        "chatId"
    )

    val dummy3 = Event(
        "id",
        "event name",
        "desciption",
        10.0,
        10.0,
        after,
        after,
        true,0,
        "chatId"
    )
    override fun getAllEvents(): List<Event> {
        return listOf(dummy, dummy2, dummy3)
    }

    override fun subscribeToEventParticipants(
        eventId: String,
        successListener: (Int) -> Unit,
        failureListener: (String) -> Unit
    ) {
        successListener(10)
    }
}