package com.github.studydistractor.sdp.eventHistory

import android.util.Log
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventParticipants
import com.github.studydistractor.sdp.data.UserData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Optional
import javax.inject.Inject

class EventHistoryServiceFirebase @Inject constructor(pathEvents: String, pathEventParticipants: String, pathUsers: String, pathEventClaimPoints: String): EventHistoryModel {
    private val db = FirebaseDatabase.getInstance()

    private val eventsPath = pathEvents //"Events"
    private val eventParticipantsPath = pathEventParticipants // "EventParticipants"
    private val usersPath = pathUsers // "Users"
    private val userClaimsPath = pathEventClaimPoints // "EventClaimPoints"

    private val eventsDatabaseRef = db.getReference(eventsPath)
    private val eventParticipantsDatabaseRef = db.getReference(eventParticipantsPath)
    private val userClaimsDatabaseRef = db.getReference(userClaimsPath)
    private val usersDatabaseRef = db.getReference(usersPath)

    private val eventsCache: MutableMap<String, Event> = mutableMapOf()
    private val participationsCache: MutableList<String> = mutableListOf()
    private val userClaimsCache: MutableSet<String> = mutableSetOf()
    private var userScoreCache: Int = 0

    private var currentHistoryListener: Optional<ValueEventListener> = Optional.empty()
    private var currentUserClaimsListener: Optional<ValueEventListener> = Optional.empty()
    private var currentPointsListener: Optional<ValueEventListener> = Optional.empty()

    private fun observePoints(userId: String) {
         if(currentPointsListener.isPresent) usersDatabaseRef.removeEventListener(currentPointsListener.get())

        currentPointsListener = Optional.of(usersDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { userDataRaw ->
                    val userData = userDataRaw.getValue(UserData::class.java)
                    if(userData != null
                        && userData.id == userId
                    ) {
                        userScoreCache = userData.score
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("EventHistoryServiceFirebase", "Database error on user points update")
            }
        }))
    }

    private fun observeClaims(userId: String) {
        if(currentUserClaimsListener.isPresent) userClaimsDatabaseRef.removeEventListener(currentUserClaimsListener.get())

        currentUserClaimsListener = Optional.of(userClaimsDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userClaimsCache.clear()
                snapshot.children.forEach { eventsClaim ->
                    val eventsClaimPoints = eventsClaim.value as Map<String, Boolean>
                    if(eventsClaimPoints != null
                        && eventsClaimPoints.containsKey(userId)
                        && eventsClaimPoints[userId] == true
                    ) {
                        userClaimsCache.add(eventsClaim.key!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("EventHistoryServiceFirebase", "Database error on point claims update")
            }

        }))
    }

    override fun observeHistory(userId: String, onHistoryChange: (List<Event>) -> Unit) {
        if(currentHistoryListener.isPresent) eventParticipantsDatabaseRef.removeEventListener(currentHistoryListener.get())

        currentHistoryListener = Optional.of(eventParticipantsDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                participationsCache.clear()
                snapshot.children.forEach {eventParticipantsRaw ->
                    val eventParticipants = eventParticipantsRaw.value as Map<String, Boolean>
                    Log.d("evhi", eventParticipants.toString())
                    if(eventParticipants != null
                        && eventParticipants.containsKey(userId)
                        && eventParticipants[userId] == true
                    ) {
                        participationsCache.add(eventParticipantsRaw.key!!)
                    }
                }

                eventsDatabaseRef.get().addOnSuccessListener {
                    val history = mutableListOf<Event>()
                    eventsCache.clear()
                    it.children.forEach { eventRaw ->
                        val event = eventRaw.getValue(Event::class.java)
                        if(event != null) {
                            eventsCache[event.eventId!!] = event
                            if(participationsCache.contains(event.eventId)) {
                                history.add(event)
                            }
                        }
                    }
                    onHistoryChange(history)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("EventHistoryServiceFirebase", "Database error on history update")
            }
        }))

        observeClaims(userId)
        observePoints(userId)
    }

    override fun claimPoints(userId: String, eventId: String): Task<Void> {
        if (participationsCache.contains(eventId)
            && !userClaimsCache.contains(eventId)
            && eventsCache.containsKey(eventId)
        ) {
            val endDate = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(eventsCache[eventId]!!.end)
            if(endDate.before(Date())) {
                userScoreCache += eventsCache[eventId]!!.numberOfPoints
                userClaimsCache.add(eventId)
                return usersDatabaseRef
                    .child(userId)
                    .child("score")
                    .setValue(userScoreCache)
                    .continueWithTask(
                        return userClaimsDatabaseRef
                            .child(eventId)
                            .child(userId)
                            .setValue(true)
                    )
            }
        }
        return Tasks.forException(Exception("Couldn't claim points"))
    }
}