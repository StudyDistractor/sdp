package com.github.studydistractor.sdp.ui.state

import com.github.studydistractor.sdp.data.Distraction
import java.util.Collections

data class DistractionListUiState(
    val distractionList: List<Distraction> = listOf(),
    val filtersExpanded: Boolean = false,
    val filtersAvailableTags: List<String> = Collections.emptyList(),
    val filtersSelectedTags: Set<String> = Collections.emptySet(),
    val filtersSelectedLength: Distraction.Length? = null
)
