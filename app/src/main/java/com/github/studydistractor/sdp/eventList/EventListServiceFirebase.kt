package com.github.studydistractor.sdp.eventList

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.FirebaseEvent
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.lang.NullPointerException

class EventListServiceFirebase : EventListModel {
    private val EVENTPATH = "Events"
    private val participantsPath = "EventParticipants"
    private val eventDatabaseRef = FirebaseDatabase.getInstance().getReference(EVENTPATH)
    private val dbParticipants = FirebaseDatabase.getInstance().getReference(participantsPath)
    val events =  mutableStateListOf<Event>()
    private val eventParticipantListeners: MutableMap<String, ValueEventListener> = mutableMapOf()

    init {
        eventDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                events.clear()
                for(distraction in snapshot.children) {
                    val eventItem = distraction.getValue(FirebaseEvent::class.java)
                    if(eventItem != null){
                        handleFirebaseEvent(eventItem)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })
    }

    override fun getAllEvents(): List<Event> {
        return events
    }

    override fun subscribeToEventParticipants(
        eventId: String,
        successListener: (Int) -> Unit,
        failureListener: (String) -> Unit
    ) {
        eventParticipantListeners[eventId] = dbParticipants.child(eventId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newEventParticipants = snapshot.getValue<Map<String, Boolean>>()
                if(newEventParticipants == null) {
                    failureListener("Error while syncing with the db")
                } else {
                    successListener(
                        newEventParticipants
                            .filterValues { value -> value }
                            .keys
                            .count()
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                failureListener(error.message)
            }
        })
    }

    private fun handleFirebaseEvent(event: FirebaseEvent) {
        try {
            events.add(event.toEvent())
        } catch (_: NullPointerException) {
            Log.d("Firebase event", "An event has non initialized fields")
        }
    }

}