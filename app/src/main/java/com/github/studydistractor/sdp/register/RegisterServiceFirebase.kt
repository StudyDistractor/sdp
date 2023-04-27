package com.github.studydistractor.sdp.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class RegisterServiceFirebase @Inject constructor() :  RegisterModel {
    private var firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Task<String> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
            .continueWith {
                it.result.user?.uid
            }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }
}