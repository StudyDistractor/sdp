package com.github.studydistractor.sdp.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/*
    This is a dummy implementation of the interface to show how Hilt works.
 */
class FirebaseLoginAuth @Inject constructor() : LoginAuthInterface {
    var auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun loginWithEmail(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }
}