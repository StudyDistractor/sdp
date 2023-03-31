package com.github.studydistractor.sdp.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/*
    This is a dummy implementation of the interface to show how Hilt works.
 */
class FirebaseLoginAuth @Inject constructor() : LoginAuthInterface {
<<<<<<< HEAD
    lateinit var auth : FirebaseAuth

    constructor(auth: FirebaseAuth) : this() {
        this.auth = auth
    }

=======
    var auth : FirebaseAuth = FirebaseAuth.getInstance()
>>>>>>> 8352636b1ec7fe6cebf193e3266ec2621f43c473
    override fun loginWithEmail(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }
}