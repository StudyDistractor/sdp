package com.github.studydistractor.sdp.login

import androidx.compose.material3.ExperimentalMaterial3Api
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/*
    This is a dummy implementation of the interface to show how Hilt works.
 */
class FirebaseLoginAuth @Inject constructor() : LoginAuthInterface {
    lateinit var auth : FirebaseAuth

    constructor(auth: FirebaseAuth) : this() {
        this.auth = auth
    }

    override fun loginWithEmail(email: String, password: String): Task<AuthResult> {
        auth = FirebaseAuth.getInstance();
        return auth.signInWithEmailAndPassword(email, password)
    }
}