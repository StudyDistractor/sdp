package com.github.studydistractor.sdp.ui.state

data class CreateUserUiState(
    val dateText: String = "",
    val year: Int = 0,
    val month: Int = 0,
    val dayOfMonth: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val phoneNumber: String = ""
)
