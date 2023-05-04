package com.github.studydistractor.sdp.ui.state

import androidx.compose.runtime.snapshots.SnapshotStateList

data class FriendsUiState(
    val newFriend: String = "",
    val friendsList: SnapshotStateList<String> = SnapshotStateList(),
)
