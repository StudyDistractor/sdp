package com.github.studydistractor.sdp.history

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseHistory : HistoryInterface {
    private var db : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun getHistory(uid: String): SnapshotStateList<HistoryEntry> {
        db = FirebaseDatabase.getInstance()
        val databaseRef = db.getReference("Users").child(uid).child("history")
        val result =  mutableStateListOf<HistoryEntry>()
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(entry in snapshot.children) {
                    val historyEntry = entry.getValue(HistoryEntry::class.java)
                    if(historyEntry != null) {
                        historyEntry!!.date = entry.key!!.toLong()
                        result.add(historyEntry)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        return result
    }


    override fun getCurrentUid(): String? {
        return auth.uid
    }

    override fun addHistoryEntry(entry : HistoryEntry,uid: String): Boolean {
        if(uid.isEmpty()) return false
        val databaseRef = db.getReference("Users").child(uid).child("history")
        databaseRef.child(entry.date.toString()).setValue(entry)
        return true
    }
}