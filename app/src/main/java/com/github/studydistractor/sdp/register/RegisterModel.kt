package com.github.studydistractor.sdp.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface RegisterModel {
    fun createUserWithEmailAndPassword(email: String, password: String): Task<String>
    fun getCurrentUser() : FirebaseUser?
    fun getCurrentUserUid() : String?

}
