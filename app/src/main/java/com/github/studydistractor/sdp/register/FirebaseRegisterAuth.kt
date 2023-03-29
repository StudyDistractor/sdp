package com.github.studydistractor.sdp.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseRegisterAuth : RegisterAuthInterface {
    lateinit var auth : FirebaseAuth
    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }
}