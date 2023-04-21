package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.register.RegisterModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseUser

class RegisterServiceFake : RegisterModel {
    override fun createUserWithEmailAndPassword(email: String, password: String): Task<String> {
        return Tasks.forResult("uid")
    }

    override fun getCurrentUser(): FirebaseUser? {
        return null
    }

    override fun getCurrentUserUid(): String? {
        return "uid"
    }
}