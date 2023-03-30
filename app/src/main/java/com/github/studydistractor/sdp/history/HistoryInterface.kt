package com.github.studydistractor.sdp.history

import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * This interface is able to get the uid of the current logged user and fetch the historic of this
 * user and add new entry if the users finished a ProcrastinationActivity
 */
interface HistoryInterface {
    /**
     * Get the mutable state list of the history entry of the selected uid user.
     */
    fun getHistory(uid : String) : SnapshotStateList<HistoryEntry>

    /**
     * Get the current uid of the user with firebase auth.
     */
    fun getCurrentUid() : String?

    /**
     * Add a new history entry to the user.
     */
    fun addHistoryEntry(entry : HistoryEntry,uid : String) : Boolean
}