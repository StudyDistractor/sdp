package com.github.studydistractor.sdp.eventHistory

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventParticipants
import com.github.studydistractor.sdp.data.FirebaseEvent
import com.github.studydistractor.sdp.data.FirebaseEventParticipants
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventHistoryServiceFirebase: EventHistoryModel {
    private val eventPaths = "Events"
    private val eventDatabaseRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(eventPaths)
    private val eventParticipantsPaths = "EventParticipants"
    private val eventParticipantsDatabaseRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(eventParticipantsPaths)
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var onEventChange: (List<Event>) -> Unit = {}
    private var onEventParticipantsChange: (List<EventParticipants>) -> Unit = {}

    private val eventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val firebaseEvents = mutableStateListOf<FirebaseEvent>()
            for (event in snapshot.children) {
                val eventItem = event.getValue(FirebaseEvent::class.java)
                if (eventItem != null) {
                    firebaseEvents.add(eventItem)
                }
            }

            val events = mutableListOf<Event>()

            for (fe in firebaseEvents) {
                val ev: Event
                try {
                    ev = fe.toEvent()
                } catch (e: Exception) {
                    continue
                }

                events.add(ev)
            }
            onEventChange(events)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase Event", "loadPost:onCancelled " + error.toException().toString())
        }
    }

    private val eventParticipantsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val firebaseEventParticipants = mutableStateListOf<FirebaseEventParticipants>()
            for (eP in snapshot.children) {
                val eventParticipantsItem = eP.getValue(FirebaseEventParticipants::class.java)
                if (eventParticipantsItem != null) {
                    firebaseEventParticipants.add(eventParticipantsItem)
                }
            }

            val eventsParticipants = mutableListOf<EventParticipants>()
            for (fep in firebaseEventParticipants){
                val ep : EventParticipants
                try {
                    ep = fep.toEventParticipants()
                } catch(e: Exception){
                    continue
                }

                eventsParticipants.add(ep)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase Event Participant", "loadPost:onCancelled " + error.toException().toString())
        }
    }

    init {
        eventDatabaseRef.addValueEventListener(eventListener)
        eventParticipantsDatabaseRef.addValueEventListener(eventParticipantsListener)
    }

    override fun observeEvents(onEventChange: (List<Event>) -> Unit){
        this.onEventChange = onEventChange
    }

    override fun observeEventsParticipants(onEventParticipantsChange: (List<EventParticipants>) -> Unit){
        this.onEventParticipantsChange = onEventParticipantsChange
    }

    override fun getCurrentUid(): Task<String> {
        val uid = auth.uid

        if (uid.isNullOrEmpty()) {
            Tasks.forException<Exception>(Exception("Empty or Null userId"))
        }

        return Tasks.forResult(uid)
    }

}