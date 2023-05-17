package com.github.studydistractor.sdp.ui.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Event

data class EventHistoryUiState(
    val eventHistory: SnapshotStateList<Event> = SnapshotStateList()
)
