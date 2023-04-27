package com.github.studydistractor.sdp.ui.state

import com.github.studydistractor.sdp.data.Distraction


data class DistractionUiState(
    val distraction: Distraction = Distraction(),
    val isBookmarked: Boolean = false
)