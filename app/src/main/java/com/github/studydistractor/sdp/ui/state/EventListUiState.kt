package com.github.studydistractor.sdp.ui.state

import com.github.studydistractor.sdp.data.Event

data class EventListUiState(
    val eventList: List<Pair<Event, Int>> = listOf()
)