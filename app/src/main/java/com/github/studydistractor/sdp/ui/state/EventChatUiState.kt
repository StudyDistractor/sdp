package com.github.studydistractor.sdp.ui.state

import com.github.studydistractor.sdp.data.Message

data class EventChatUiState(
    val eventId : String = "",
    val participants : List<String> = listOf(),
    val message : String = "",
    val messages : List<Message> = listOf(),
)
