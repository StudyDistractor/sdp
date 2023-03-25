package com.github.studydistractor.sdp

import android.util.Log
import javax.inject.Inject

/*
    This is a dummy implementation of the interface to show how Hilt works.
 */
class FirebaseLoginAuth @Inject constructor() : LoginAuthInterface {
    override fun login(email: String, password: String) {
        Log.d("FirebaseLoginAuth","Using firebase login auth !")
    }
}