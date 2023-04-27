package com.github.studydistractor.sdp.ui.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.HistoryEntry

data class HistoryUiState(
    val historyEntries: SnapshotStateList<HistoryEntry> = SnapshotStateList(),
)