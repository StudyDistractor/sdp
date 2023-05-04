package com.github.studydistractor.sdp.distractionList

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Distraction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class DistractionListServiceFirebase @Inject constructor() : DistractionListModel {
    private val pathStringProcrastinationActivity = "ProcrastinationActivities"
    private val pathStringTags = "Tags"
    private val distractionDatabaseRef : DatabaseReference = FirebaseDatabase.getInstance().getReference(pathStringProcrastinationActivity)
    private val tagsDatabaseRef : DatabaseReference = FirebaseDatabase.getInstance().getReference(pathStringTags)
    val distractions =  mutableStateListOf<Distraction>()
    val tags = mutableStateListOf<String>()

    init {
        distractionDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                distractions.clear()
                for(distraction in snapshot.children) {
                    val distractionItem = distraction.getValue(Distraction::class.java)
                    if(distractionItem != null){
                        distractionItem.distractionId = distraction.key
                        distractions.add(distractionItem)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        tagsDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tags.clear()
                for(id in snapshot.children) {
                    val tag = id.getValue(String::class.java)
                    if(tag != null) {
                        tags.add(tag)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

    }

    override fun getAllDistractions(): SnapshotStateList<Distraction> {
        return distractions
    }

    override fun getTags(): List<String> {
        return tags
    }

    /**
     * This is a workaround for the maps
     * TODO: When maps are move to screen remove this function
     */
    fun fetchDistractionsCallBack(callback: (List<Distraction>) -> Unit) {
        val result =  mutableStateListOf<Distraction>()
        distractionDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(activity in snapshot.children) {
                    val activityItem = activity.getValue(Distraction::class.java)
                    if(activityItem != null) {
                        result.add(activityItem)
                    }
                }
                callback(result)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
                callback(emptyList())
            }
        })
    }
}