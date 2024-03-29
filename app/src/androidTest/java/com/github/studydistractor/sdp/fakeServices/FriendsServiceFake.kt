package com.github.studydistractor.sdp.fakeServices

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.account.FriendsModel
import com.github.studydistractor.sdp.data.HistoryEntry
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class FriendsServiceFake : FriendsModel {
    val friendlist = setOf("friend1", "friend2")

    override fun getCurrentUid(): String {
        return "uid"
    }

    override fun addNewFriend(uid1: String, uid2: String): Task<Void> {
        return Tasks.whenAll(setOf(Tasks.forResult(uid2))) // simply succeeds immediately
    }

    override fun removeFriend(uid1: String, uid2: String): Task<Void> {
        return Tasks.whenAll(setOf(Tasks.forResult(""))) // simply succeeds immediately
    }

    override fun fetchAllFriends(uid: String): SnapshotStateList<String> {
        val list = mutableStateListOf<String>()
        list.addAll(friendlist)
        return list
    }

    override fun observeFriendHistory(uid: String, onChange: (List<HistoryEntry>) -> Unit) {
        onChange(listOf(HistoryEntry("test_name", "test_description")))
    }
}