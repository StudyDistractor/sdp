package com.github.studydistractor.sdp.distraction

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

/**
 * A class that implements the distraction service to talk with the Firebase realtime database
 */
class FireBaseDistractionService @Inject constructor(): DistractionService {
    private val pathStringProcrastinationActivity = "ProcrastinationActivities"
    private val databaseRef : DatabaseReference = FirebaseDatabase.getInstance().getReference(pathStringProcrastinationActivity)
    val result =  mutableStateListOf<Distraction>()

    override fun fetchDistractions(): SnapshotStateList<Distraction> {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(activity in snapshot.children) {
                        val activityItem = activity.getValue(Distraction::class.java)
                        if(activityItem != null) {
                            result.add(activityItem)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())

                }
            })

        return result
    }

    override fun postDistraction(activity: Distraction) {
        databaseRef.child(activity.name!!).setValue(activity)
    }

    /**
     * This is a workaround for the maps
     * TODO: When maps are move to screen remove this function
     */
    fun fetchDistractionsCallBack(callback: (List<Distraction>) -> Unit) {
        val result =  mutableStateListOf<Distraction>()
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
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