package com.github.studydistractor.sdp.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface LoginModel {
    fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Task<String>
}