package com.github.studydistractor.sdp.ui.state

import com.github.studydistractor.sdp.data.Event

data class EventUiState(
    val userId: String = "",
    val eventId: String = "",
    val event: Event = Event(),
    val toggleParticipationButtonText: String = "",
    val participantsHeadingText: String = "",
    val participating: Boolean = false,
    val participants: List<String> = listOf(),
    val canParticipate: Boolean = false
)
