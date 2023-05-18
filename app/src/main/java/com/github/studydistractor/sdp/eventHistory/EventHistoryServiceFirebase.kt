package com.github.studydistractor.sdp.eventHistory

import android.util.Log
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventClaimPoints
import com.github.studydistractor.sdp.data.EventParticipants
import com.github.studydistractor.sdp.data.FirebaseEvent
import com.github.studydistractor.sdp.data.FirebaseEventClaimPoints
import com.github.studydistractor.sdp.data.FirebaseEventParticipants
import com.github.studydistractor.sdp.data.FirebaseUserData
import com.github.studydistractor.sdp.data.UserData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventHistoryServiceFirebase: EventHistoryModel {
    private val db = FirebaseDatabase.getInstance()

    private val eventPaths = "Events"
    private val eventParticipantsPaths = "EventParticipants"
    private val eventClaimPointsPath = "EventClaimPoints"
    private val usersPath = "Users"

    private val eventDatabaseRef = db.getReference(eventPaths)
    private val eventParticipantsDatabaseRef = db.getReference(eventParticipantsPaths)
    private val eventClaimPointsDatabaseRef = db.getReference(eventClaimPointsPath)
    private val usersDatabaseRef = db.getReference(usersPath)

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var onEventChange: (List<Event>) -> Unit = {}
    private var onEventParticipantsChange: (List<EventParticipants>) -> Unit = {}
    private var onEventClaimPointsChange: (List<EventClaimPoints>) -> Unit = {}
    private var onCurrentUserChange: (UserData) -> Unit = {}

    private val eventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val events = mutableListOf<Event>()
            for (children in snapshot.children) {
                val firebaseEvent = children.getValue(FirebaseEvent::class.java)
                if (firebaseEvent != null) {
                    try {
                        val event = firebaseEvent.toEvent()
                        events.add(event)
                    } catch (e: Exception) {
                        continue
                    }
                }
            }
            Log.d("Event", "${events.size}")
            onEventChange(events)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase Event", "loadPost:onCancelled " + error.toException().toString())
        }
    }
    private val eventParticipantsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val eventsParticipants = mutableListOf<EventParticipants>()
            for (children in snapshot.children) {
                val fep = children.getValue(FirebaseEventParticipants::class.java)
                if (fep != null) {
                    try {
                        val ep = fep.toEventParticipants()
                        eventsParticipants.add(ep)
                    } catch(e: Exception){
                        continue
                    }
                }
            }
            Log.d("EventParticipants", "${eventsParticipants.size}")
            onEventParticipantsChange(eventsParticipants)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase Event Participant", "loadPost:onCancelled " + error.toException().toString())
        }
    }
    private val eventClaimPointsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val eventClaimPoints = mutableListOf<EventClaimPoints>()
            for (children in snapshot.children) {
                val fcp = children.getValue(FirebaseEventClaimPoints::class.java)
                if (fcp != null) {
                    try {
                        val cp = fcp.toEventClaimPoints()
                        eventClaimPoints.add(cp)
                    } catch(e: Exception){
                        continue
                    }
                }
            }
            onEventClaimPointsChange(eventClaimPoints)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase Event Claim Points", "loadPost:onCancelled " + error.toException().toString())
        }
    }
    private val userListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (children in snapshot.children){
                val firebaseUserData = children.getValue(FirebaseUserData::class.java)
                if (firebaseUserData != null){
                    val userData : UserData
                    try {
                        userData = firebaseUserData.toUserData()
                    } catch (e: Exception){
                        continue
                    }
                    if (!auth.uid.isNullOrEmpty()
                        && userData.id == auth.uid
                    ){
                        onCurrentUserChange(userData)
                    }

                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase User", "loadPost:onCancelled " + error.toException().toString())
        }
    }

    init {
        eventDatabaseRef.addValueEventListener(eventListener)
        eventParticipantsDatabaseRef.addValueEventListener(eventParticipantsListener)
        eventClaimPointsDatabaseRef.addValueEventListener(eventClaimPointsListener)
        usersDatabaseRef.addValueEventListener(userListener)
    }

    override fun observeEvents(onEventChange: (List<Event>) -> Unit){
        this.onEventChange = onEventChange
    }

    override fun observeEventsParticipants(onEventParticipantsChange: (List<EventParticipants>) -> Unit){
        this.onEventParticipantsChange = onEventParticipantsChange
    }

    override fun observeEventClaimPoints(onEventClaimPointsChange: (List<EventClaimPoints>) -> Unit) {
        this.onEventClaimPointsChange = onEventClaimPointsChange
    }

    override fun observeCurrentUser(onCurrentUserChange: (UserData) -> Unit) {
        this.onCurrentUserChange = onCurrentUserChange
    }

    override fun postUser(userData: UserData): Task<Void>{
        return usersDatabaseRef.child(userData.id).setValue(userData)
    }

    override fun postEventClaimPoints(eventClaimPoints: EventClaimPoints): Task<Void> {
        return eventClaimPointsDatabaseRef.child(eventClaimPoints.eventId).setValue(eventClaimPoints)
    }
}