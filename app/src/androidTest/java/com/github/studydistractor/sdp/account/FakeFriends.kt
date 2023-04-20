package com.github.studydistractor.sdp.account

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class FakeFriends : FriendsInterface {
    private val UID = "TEST"
    var friendlist = mutableStateListOf("FRIEND1", "FRIEND2")
    override fun getCurrentUid(): String {
        return UID
    }

    override fun addNewFriend(
        uid1: String,
        uid2: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        friendlist.add(uid2)
        onSuccess()
        onFailed()
    }

    override fun removeFriend(
        uid1: String,
        uid2: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        friendlist.remove(uid2)
        onSuccess()
        onFailed()
    }

    override fun fetchAllFriends(uid: String): SnapshotStateList<String> {
        return friendlist
    }
}