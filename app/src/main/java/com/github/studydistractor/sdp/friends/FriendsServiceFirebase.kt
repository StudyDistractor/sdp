package com.github.studydistractor.sdp.account

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.HistoryEntry
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FriendsServiceFirebase @Inject constructor(): FriendsModel {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val FRIENDSPATH = "Friends"

    private var friends = mutableStateListOf<String>()

    private var friendListener =  object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
           friends.clear()
            for (id in snapshot.children) {
                if (id.exists()) {
                    friends.add(id.getValue(String::class.java)!!)
                }
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
        }
    }


    init {
        if(auth.uid != null){
            val databaseRef = db.getReference(FRIENDSPATH).child(auth.uid!!)
            databaseRef.addValueEventListener(friendListener)
        }
    }
    override fun getCurrentUid(): String? {
        return auth.uid
    }

    override fun addNewFriend(
        uid1: String,
        uid2: String,
    ) : Task<Void> {
        // TODO: what happens if the first update succeeds but not the second?
        return db.getReference(FRIENDSPATH).child(uid1).child(uid2).setValue(uid2)
            .continueWithTask {
                db.getReference(FRIENDSPATH).child(uid2).child(uid1).setValue(uid1)
            }
    }

    override fun removeFriend(
        uid1: String,
        uid2: String
    ) : Task<Void> {
        return db.getReference(FRIENDSPATH).child(uid1).child(uid2).removeValue()
            .continueWithTask {
                db.getReference(FRIENDSPATH).child(uid2).child(uid1).removeValue()
            }
    }

    override fun fetchAllFriends(uid: String): SnapshotStateList<String> {
        return friends
    }

    override fun observeFriendHistory(uid : String, onChange: (List<HistoryEntry>) -> Unit) {
        db.getReference("History").child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val friendHistory = arrayListOf<HistoryEntry>()
                for(history in snapshot.children){
                    val historyEntry = history.getValue(HistoryEntry::class.java)
                    if(historyEntry != null) {
                        friendHistory.add(historyEntry)
                    }
                }
                onChange(friendHistory)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}