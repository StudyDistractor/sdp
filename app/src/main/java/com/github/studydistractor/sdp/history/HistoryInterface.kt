package com.github.studydistractor.sdp.history

import androidx.compose.runtime.snapshots.SnapshotStateList

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