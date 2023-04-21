package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.login.LoginModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class LoginServiceFake : LoginModel {
    override fun loginWithEmailAndPassword(email: String, password: String): Task<String> {
        return Tasks.forResult("uid")
    }
}