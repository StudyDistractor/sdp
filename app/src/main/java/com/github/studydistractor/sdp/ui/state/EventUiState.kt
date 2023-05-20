package com.github.studydistractor.sdp.ui.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Event

data class EventUiState(
    val userId: String = "",
    val eventId: String = "",
    val event: Event = Event(),
    val toggleParticipationButtonText: String = "",
    val participantsHeadingText: String = "",
    val participating: Boolean = false,
    val participants: MutableList<String> = mutableListOf(),
    val canParticipate: Boolean = false
)
