package com.github.studydistractor.sdp.ui.state

import com.github.studydistractor.sdp.data.Distraction

data class DailyChallengeUiState(
    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,
    val dateString: String = "",
    val checkedStates: List<Boolean> = listOf(),
    val distractionsToDisplay: List<Distraction> = listOf(),
    val allChecked: Boolean = false,
)

