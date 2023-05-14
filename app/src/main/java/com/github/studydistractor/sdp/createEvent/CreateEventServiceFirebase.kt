package com.github.studydistractor.sdp.createEvent

import com.github.studydistractor.sdp.data.FirebaseChat
import com.github.studydistractor.sdp.data.FirebaseEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateEventServiceFirebase : CreateEventModel {
    private val eventsDB: DatabaseReference = FirebaseDatabase.getInstance().getReference("Events")
    private val chatDB: DatabaseReference = FirebaseDatabase.getInstance().getReference("ChatEvent")
    override fun createEvent(eventInformation: Map<String, Any?>): Task<Void> {
        val chatRef = chatDB.push()
        val eventRef = eventsDB.push()
        chatRef.setValue(FirebaseChat(chatRef.key as String))
        return eventRef.setValue(
            FirebaseEvent(
                eventRef.key as String,
                eventInformation["name"] as String, eventInformation["description"] as String,
                eventInformation["latitude"] as Double, eventInformation["longitude"] as Double,
                eventInformation["startDateTime"] as String, eventInformation["endDateTime"] as String,
                eventInformation["lateParticipationAllowed"] as Boolean, eventInformation["points"] as Int,
                chatRef.key as String
            )
        )

    }
}