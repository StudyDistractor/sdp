package com.github.studydistractor.sdp.procrastinationActivity

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
 * A class that implements the procrastination service to talk with the Firebase realtime database
 */
class FireBaseProcrastinationActivityService @Inject constructor(): ProcrastinationActivityService {
    private val pathStringProcrastinationActivity = "ProcrastinationActivities"
    private val databaseRef : DatabaseReference = FirebaseDatabase.getInstance().getReference(pathStringProcrastinationActivity)

    override fun fetchProcrastinationActivities(callback: (List<ProcrastinationActivity>) -> Unit) {
        val result =  mutableStateListOf<ProcrastinationActivity>()
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(activity in snapshot.children) {
                        val activityItem = activity.getValue(ProcrastinationActivity::class.java)
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

    override fun postProcastinationActivities(activity: ProcrastinationActivity) {
        databaseRef.child(activity.name!!).setValue(activity)
    }
}