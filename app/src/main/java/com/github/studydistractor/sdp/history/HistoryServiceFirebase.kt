package com.github.studydistractor.sdp.history

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.HistoryEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.inject.Inject

/**
 * This class is the implementation of the interface for historyActivity using Firebase
 *
 */
class HistoryServiceFirebase @Inject constructor(): HistoryModel {
    private var db : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val historyPath : String = "History"
    private val history =  mutableStateListOf<HistoryEntry>()
    override fun getHistory(uid: String): SnapshotStateList<HistoryEntry> {
        db = FirebaseDatabase.getInstance()
        val databaseRef = db.getReference(historyPath).child(uid)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                history.clear()
                for(entry in snapshot.children) {
                    try {
                        val historyEntry = entry.getValue(HistoryEntry::class.java)
                        if(historyEntry != null) {
                            history.add(historyEntry)
                        }
                    } catch (e : DatabaseException){
                        Log.d("Firebase", "unable to transform to history entry")
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
        return auth.uid
    }

    override fun addHistoryEntry(entry : HistoryEntry, uid: String): Boolean {
        if(uid.isEmpty()) return false
        val databaseRef = db.getReference(historyPath).child(uid)
        databaseRef.child(entry.date.toString()).setValue(entry)
        return true
    }
}