package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.createEvent.CreateEventModel
import com.github.studydistractor.sdp.data.Event
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class CreateEventServiceFake : CreateEventModel {
    override fun createEvent(event: Event): Task<Void> {
        return Tasks.whenAll(setOf(Tasks.forResult("")))
    }
}