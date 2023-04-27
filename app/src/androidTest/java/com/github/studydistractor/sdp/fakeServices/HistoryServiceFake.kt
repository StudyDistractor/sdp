package com.github.studydistractor.sdp.fakeServices

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.github.studydistractor.sdp.data.HistoryEntry
import com.github.studydistractor.sdp.history.HistoryModel

class HistoryServiceFake : HistoryModel {
    private val history: MutableMap<String, MutableSet<HistoryEntry>> = mutableMapOf(
        "uid" to mutableSetOf(HistoryEntry("name", "description", 1000L))
    )

    override fun getHistory(uid: String): SnapshotStateList<HistoryEntry> {
        return if(history.containsKey(uid)) {
            history[uid]!!.toMutableStateList()
        } else {
            mutableStateListOf()
        }
    }

    override fun getCurrentUid(): String {
        return "uid"
    }

    override fun addHistoryEntry(entry: HistoryEntry, uid: String): Boolean {
        return if(history.containsKey(uid)) {
            history[uid]!!.add(entry)
        } else {
            history.put(uid, mutableSetOf(entry)) != null
        }
    }
}