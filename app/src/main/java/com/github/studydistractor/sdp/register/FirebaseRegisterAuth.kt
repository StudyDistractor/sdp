package com.github.studydistractor.sdp.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseRegisterAuth @Inject constructor() :  RegisterAuthInterface {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()

    constructor(auth: FirebaseAuth) : this() {
        this.auth = auth
    }
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