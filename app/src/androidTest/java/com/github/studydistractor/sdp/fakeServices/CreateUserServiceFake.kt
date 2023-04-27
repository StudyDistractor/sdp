package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.createUser.CreateUserModel
import com.github.studydistractor.sdp.data.UserData
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class CreateUserServiceFake : CreateUserModel {
    override fun createUser(user: UserData): Task<Void> {
        return Tasks.whenAll(setOf(Tasks.forResult(""))) // simply succeeds immediately
    }
}