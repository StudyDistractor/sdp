package com.github.studydistractor.sdp.createEvent

import com.github.studydistractor.sdp.data.Event
import com.google.android.gms.tasks.Task

interface CreateEventModel {
    /**
     * Create an event in the db
     * @event(Event): the event to add
     */
    fun createEvent(event: Event): Task<Void>
}