package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.UserData
import com.github.studydistractor.sdp.user.UserModel
import java.util.Collections

class UserServiceFake : UserModel {
    override fun fetchUsers(): List<UserData> {
        return Collections.emptyList()
    }

    override fun postUser(user: UserData) {
        return
    }
}