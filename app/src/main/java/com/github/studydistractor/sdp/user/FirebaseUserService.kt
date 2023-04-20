package com.github.studydistractor.sdp.user

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.database.*
import javax.inject.Inject

class FirebaseUserService @Inject constructor(): UserService {
    private val path = "Users"
    private val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference(path)
    var users : SnapshotStateList<UserData> = SnapshotStateList()


    init {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            val newUsers = mutableStateListOf<UserData>()
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("snapshot key", snapshot.key!!)
                for (user in snapshot.children) {
                    Log.d("User key", snapshot.key!!)
                    val userItem = user.getValue(UserData::class.java)
                    if (userItem != null) {
                        newUsers.add(userItem)
                    }
                }
                users = newUsers
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })
    }

    override fun fetchUsers(): List<UserData> {
        return users
    }

    override fun postUser(
        user: UserData,
    ) {
        databaseRef.child(user.id!!).setValue(user)
    }
}