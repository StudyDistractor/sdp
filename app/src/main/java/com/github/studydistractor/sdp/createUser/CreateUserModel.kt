package com.github.studydistractor.sdp.createUser

import com.github.studydistractor.sdp.data.UserData
import com.google.android.gms.tasks.Task

interface CreateUserModel {
    /**
     * Add a user to the db
     *
     * @user(UserData): the user to add to the db
     */
    fun createUser(user: UserData): Task<Void>
}