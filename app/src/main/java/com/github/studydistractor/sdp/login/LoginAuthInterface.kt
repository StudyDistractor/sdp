package com.github.studydistractor.sdp.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface LoginAuthInterface {
    fun loginWithEmail(email: String, password: String) : Task<AuthResult>
}