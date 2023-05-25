package com.github.studydistractor.sdp.serviceTests

import com.github.studydistractor.sdp.createEvent.CreateEventServiceFirebase
import com.github.studydistractor.sdp.data.Event
import org.junit.Test
import java.util.Date

class CreateEventServiceFirebaseTest {

    private val service = CreateEventServiceFirebase("TestEvents")

    @Test
    fun testCreateEvent() {
        val event = Event(
            null,
            "TestEvent",
            "TestDescription",
            0.0,
            0.0,
            "start date",
            "end date",
            true,
            0,
            null
        )
        val task = service.createEvent(event)
        Thread.sleep(500)
        assert(task.isSuccessful)
    }
}