package com.github.studydistractor.sdp.serviceTests

import com.github.studydistractor.sdp.eventList.EventListServiceFirebase
import org.junit.Test

class EventListServiceFirebaseTest {

    private val service = EventListServiceFirebase("TestEvents")

    @Test
    fun testGetAllEvents() {
        val events = service.getAllEvents()
        Thread.sleep(500)
        assert(events.isNotEmpty())
    }
}