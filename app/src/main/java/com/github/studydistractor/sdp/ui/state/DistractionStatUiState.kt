package com.github.studydistractor.sdp.ui.state

data class DistractionStatUiState(
    val did : String = "",
    val likes: Int = 0,
    val dislikes: Int = 0,
    val feedback : String = "",
    val tag : String = "",
    val feedbacks : List<String> = listOf(),
    val tags : List<String> = listOf(),
)


