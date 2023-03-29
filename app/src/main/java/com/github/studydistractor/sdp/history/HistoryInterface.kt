package com.github.studydistractor.sdp.history

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.ProcrastinationActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

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