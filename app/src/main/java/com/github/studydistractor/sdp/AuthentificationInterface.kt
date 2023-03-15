package com.github.studydistractor.sdp

import android.service.autofill.UserData
import com.google.firebase.auth.FirebaseAuth

interface AuthentificationInterface {

    var auth: FirebaseAuth

    fun register(user: UserData)

    fun login(username: String, passwd: String);
}