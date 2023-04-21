package com.github.studydistractor.sdp.distractionList

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.github.studydistractor.sdp.data.Distraction
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class DistractionListServiceFirebase @Inject constructor() : DistractionListModel {
    private val pathStringProcrastinationActivity = "ProcrastinationActivities"
    private val databaseRef : DatabaseReference = FirebaseDatabase.getInstance().getReference(pathStringProcrastinationActivity)
    val distractions =  mutableStateListOf<Distraction>()

    private val availableTags = listOf<String>("Food", "Drink", "Sport")

    init {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(activity in snapshot.children) {
                    val activityItem = activity.getValue(Distraction::class.java)
                    if(activityItem != null) {
                        distractions.add(activityItem)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })
    }

    override fun getAllDistractions(): Task<List<Distraction>> {
        return if(distractions.isNotEmpty()) {
            Tasks.forResult(distractions)
        } else {
            databaseRef.get().onSuccessTask { data ->
                Tasks.forResult(
                    data.children
                        .mapNotNull { it.getValue(Distraction::class.java) }
                )
            }
        }
    }

    override fun getFilteredDistractions(
        length: Distraction.Length?,
        tags: Set<String>
    ): Task<List<Distraction>> {
        return getAllDistractions().onSuccessTask { list ->
            Tasks.forResult(
                list.filter {
                    (length == null || it.length == length)
                        && (tags.isEmpty() || tags.containsAll(it.tags.orEmpty()))
                }
            )
        }
    }

    override fun getAvailableTags(): List<String> {
        return availableTags
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