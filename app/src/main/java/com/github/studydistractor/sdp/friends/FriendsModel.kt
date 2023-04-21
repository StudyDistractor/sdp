package com.github.studydistractor.sdp.friends

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.android.gms.tasks.Task

/**
 * Interface to fetch User Friend Data.
 * Fetch the list of friend
 * Add and remove friend
 */
interface FriendsModel {

    /**
     * Get the current uid of the user with firebase auth.
     */
    fun getCurrentUid() : String?

    /**
     * Add new friend to the database
     * @param uid1 Friend with uid2
     * @param uid2 Friend with uid1
     */
    fun addNewFriend(
        uid1 : String,
        uid2 : String
    ) : Task<Void>

    /**
     * Remove friend to the database
     * @param uid1 Friend with uid2
     * @param uid2 Friend with uid1
     */
    fun removeFriend(
        uid1 : String,
        uid2 : String,
    ) : Task<Void>

    /**
     * Fetch all the friend of all the user uid
     * @param uid of the user to fetch friend
     */
    fun fetchAllFriends(uid : String) : SnapshotStateList<String>
}