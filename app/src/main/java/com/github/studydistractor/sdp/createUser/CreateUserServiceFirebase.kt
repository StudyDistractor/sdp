package com.github.studydistractor.sdp.createUser

import com.github.studydistractor.sdp.data.UserData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateUserServiceFirebase : CreateUserModel {
    private val databasePath = "Users"
    private val databaseRef : DatabaseReference = FirebaseDatabase.getInstance().getReference(databasePath)
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun createUser(user: UserData): Task<Void> {
        val uid = firebaseAuth.uid ?: return Tasks.forException(Exception("User is not in the db"))
        return databaseRef.child(uid).setValue(user)
    }
}