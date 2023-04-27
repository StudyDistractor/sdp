package com.github.studydistractor.sdp.friends

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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

    private var friendListener =  object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newFriend =  mutableStateListOf<String>()
            for (id in snapshot.children) {
                if (id.exists()) {
                    newFriend.add(id.getValue(String::class.java)!!)
                }
                friends = newFriend

            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
        }
    }

    private val FRIENDSPATH = "Friends"

    private var friends = SnapshotStateList<String>()

    init {
        val databaseRef = db.getReference(FRIENDSPATH).child(auth.uid!!)
        databaseRef.addListenerForSingleValueEvent(friendListener)
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
                db.getReference(FRIENDSPATH).child(uid2).child(uid1).setValue(uid2)
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
}