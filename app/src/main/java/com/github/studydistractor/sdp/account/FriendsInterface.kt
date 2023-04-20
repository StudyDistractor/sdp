package com.github.studydistractor.sdp.account

import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Interface to fetch User Friend Data.
 * Fetch the list of friend
 * Add and remove friend
 */
interface FriendsInterface {

    /**
     * Get the current uid of the user with firebase auth.
     */
    fun getCurrentUid() : String?

    /**
     * Add new friend to the database
     * @param uid1 Friend with uid2
     * @param uid2 Friend with uid1
     * @param onSuccess Run when the friend as successfully been added
     * @param onFailed Run when the friend as failed to been added
     */
    fun addNewFriend(
        uid1 : String,
        uid2 : String,
        onSuccess : () -> Unit,
        onFailed : () -> Unit,
    )

    /**
     * Remove friend to the database
     * @param uid1 Friend with uid2
     * @param uid2 Friend with uid1
     * @param onSuccess Run when the friend as successfully been removed
     * @param onFailed Run when the friend as failed to been removed
     */
    fun removeFriend(
        uid1 : String,
        uid2 : String,
        onSuccess : () -> Unit,
        onFailed : () -> Unit,
    )

    /**
     * Fetch all the friend of all the user uid
     * @param uid of the user to fetch friend
     */
    fun fetchAllFriends(uid : String) : SnapshotStateList<String>
}