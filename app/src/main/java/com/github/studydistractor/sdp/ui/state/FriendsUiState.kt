package com.github.studydistractor.sdp.ui.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.HistoryEntry

data class FriendsUiState(
    val newFriend: String = "",
    val friendsList: SnapshotStateList<String> = SnapshotStateList(),
    val friendHistory : List<HistoryEntry> = listOf()
)
