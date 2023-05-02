package com.github.studydistractor.sdp.history

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.inject.Inject

/**
 * This class is the implementation of the interface for historyActivity using Firebase
 *
 */
class HistoryServiceFirebase @Inject constructor(): HistoryModel {
    private var firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val historyPath : String = "History"
    private val history =  mutableStateListOf<HistoryEntry>()
    override fun getHistory(uid: String): SnapshotStateList<HistoryEntry> {
        firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseRef = firebaseDatabase.getReference(historyPath).child(uid)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                history.clear()
                for(entry in snapshot.children) {
                    val historyEntry = entry.getValue(HistoryEntry::class.java)
                    if(historyEntry != null) {
                        historyEntry.date = entry.key!!.toLong()
                        history.add(historyEntry)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        return history
    }


    override fun getCurrentUid(): String? {
        return firebaseAuth.uid
    }

    override fun addHistoryEntry(entry : HistoryEntry, uid: String): Boolean {
        if(uid.isEmpty()) return false
        val databaseRef = firebaseDatabase.getReference("Users").child(uid).child("history")
        databaseRef.child(entry.date.toString()).setValue(entry)
        return true
    }
}