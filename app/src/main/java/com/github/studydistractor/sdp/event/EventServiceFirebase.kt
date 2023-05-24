package com.github.studydistractor.sdp.event

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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

    lateinit var currentParticipantListeners: ValueEventListener
    private var hasParticipantsListener = false
    val participants2 = mutableStateListOf<String>()

    override fun changeParticipantListener(eventId: String) {
        if(eventId.isEmpty()) return
        if(hasParticipantsListener) {
            dbParticipants.child(eventId).removeEventListener(currentParticipantListeners)
        }

        currentParticipantListeners = dbParticipants.child(eventId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                participants2.clear()
                for(participant in snapshot.children) {
                    val participation = participant.getValue<Boolean>()
                    if(participation != null && participation) {
                        participants2.add(participant.key!!)
                    }
                }
                Log.d("Firebase", "Update participant : " + participants2.count())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })
    }
    override fun getParticipants(): SnapshotStateList<String> {
        return participants2
    }

    override fun subscribeToUserId(
        successListener: (String) -> Unit,
        failureListener: (String) -> Unit
    ) {
       auth.addAuthStateListener {
           successListener(it.uid.orEmpty())
       }
    }

    override fun isParticipating(eventId: String): Boolean {
        if(auth.uid == null) {
            return false
        }
        return participants2.contains(auth.uid)
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