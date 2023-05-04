package com.github.studydistractor.sdp.user

import com.github.studydistractor.sdp.data.UserData

interface UserModel {
    /**
     * Fetch the user from the database
     * @param callback the callback to be called when the users are fetched
     */
    fun fetchUsers(): List<UserData>

    /**
     * Post the UserData to the database
     * @param user the UserData to be posted to the database
     */
    fun postUser(user: UserData)

}