package com.github.studydistractor.sdp.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/*
    This is a dummy implementation of the interface to show how Hilt works.
 */
class LoginServiceFirebase @Inject constructor() : LoginModel {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Task<String> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
            .continueWith { it.result.user?.uid.orEmpty() }
    }
}