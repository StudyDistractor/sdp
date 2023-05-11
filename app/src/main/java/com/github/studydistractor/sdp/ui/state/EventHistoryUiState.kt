package com.github.studydistractor.sdp.ui.state

import com.github.studydistractor.sdp.data.Event

data class EventHistoryUiState(
    val eventHistory: List<Event> = listOf(),
    val userId: String = ""
)
