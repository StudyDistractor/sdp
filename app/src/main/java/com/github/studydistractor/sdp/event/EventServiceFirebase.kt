package com.github.studydistractor.sdp.event

import com.github.studydistractor.sdp.data.Event
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class EventServiceFirebase: EventModel {
    private val eventsPath = "Events"
    private val participantsPath = "EventParticipants"
    private val db = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val dbEvents = db.getReference(eventsPath)
    private val dbParticipants = db.getReference(participantsPath)

    private val eventListeners: MutableMap<String, ValueEventListener> = mutableMapOf()
    private val eventParticipantListeners: MutableMap<String, ValueEventListener> = mutableMapOf()

    override fun subscribeToEvent(
        eventId: String,
        successListener: (Event) -> Unit,
        failureListener: (String) -> Unit
    ) {
        val event = dbEvents.child(eventId)
        eventListeners[eventId] = event.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newEvent = snapshot.getValue<Event>()
                if(newEvent == null) {
                    failureListener("Error while syncing with the db")
                } else {
                    successListener(newEvent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                failureListener(error.message)
            }
        })
    }

    override fun subscribeToEventParticipants(
        eventId: String,
        successListener: (List<String>) -> Unit,
        failureListener: (String) -> Unit
    ) {
        if(eventId.isEmpty()) return

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
                            .toList()
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                failureListener(error.message)
            }
        })
    }

    override fun unsubscribeFromAllEventParticipants() {
        eventParticipantListeners.forEach { (eventId, listener) ->
            dbParticipants.child(eventId).removeEventListener(listener)
        }
        eventParticipantListeners.clear()
    }

    override fun unsubscribeFromAllEvents() {
        eventListeners.forEach { (eventId, listener) ->
            dbEvents.child(eventId).removeEventListener(listener)
        }
        eventListeners.clear()
    }

    override fun subscribeToUserId(
        successListener: (String) -> Unit,
        failureListener: (String) -> Unit
    ) {
       auth.addAuthStateListener {
           successListener(it.uid.orEmpty())
       }
    }

    override fun getEvent(eventId: String): Task<Event> {
        val event = dbEvents.child(eventId)
        return event.get().continueWith{
            it.result.getValue(Event::class.java) // TODO: what if this conversion fails?
        }
    }

    override fun isParticipating(eventId: String, userId: String): Task<Boolean> {
        return dbParticipants.child(eventId)
            .child(userId)
            .get()
            .continueWith {
                return@continueWith it.result.value as Boolean
            }
    }

    override fun addParticipant(eventId: String, userId: String): Task<Void> {
        val participants = dbParticipants.child(eventId)
        return participants.updateChildren(mapOf(userId to true))
    }

    override fun removeParticipant(eventId: String, userId: String): Task<Void> {
        val participants = dbParticipants.child(eventId)
        return participants.updateChildren(mapOf(userId to false))
    }
}