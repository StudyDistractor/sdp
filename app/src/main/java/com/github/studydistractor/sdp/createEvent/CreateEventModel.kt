package com.github.studydistractor.sdp.createEvent

import com.google.android.gms.tasks.Task

interface CreateEventModel {
    /**
     * Create an event in the db
     * @event(Event): the event to add
     */
    fun createEvent(eventInformation: Map<String, Any?>): Task<Void>
}