package com.github.studydistractor.sdp.createEvent

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.FirebaseChat
import com.github.studydistractor.sdp.data.FirebaseEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class CreateEventServiceFirebase @Inject constructor(eventsDBPath: String): CreateEventModel {
    private val eventsDB: DatabaseReference = FirebaseDatabase.getInstance().getReference(eventsDBPath)
    private val chatDB: DatabaseReference = FirebaseDatabase.getInstance().getReference("ChatEvent")
    override fun createEvent(event: Event): Task<Void> {
        val chatRef = chatDB.push()
        val eventRef = eventsDB.push()
        chatRef.setValue(FirebaseChat(chatRef.key as String))
        return eventRef.setValue(
            FirebaseEvent(
                eventRef.key as String,
                event.name, event.description,
                event.lat, event.long,
                event.start, event.end,
                event.lateParticipation,
                event.numberOfPoints,
                chatRef.key as String
            )
        )

    }
}