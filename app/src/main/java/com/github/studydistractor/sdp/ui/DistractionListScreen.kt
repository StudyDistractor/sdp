package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distractionList.DistractionListViewModel
import com.github.studydistractor.sdp.ui.components.DistractionListFilterPanel
import com.github.studydistractor.sdp.ui.components.ListedDistraction

/**
 * Screens that represent a list of scrollable distraction that we can then filter
 */
@Composable
fun DistractionListScreen(
    onDistractionClicked: (Distraction) -> Unit,
    distractionListViewModel: DistractionListViewModel
) {
    val uiState by distractionListViewModel.uiState.collectAsState()

    Column {
        DistractionListFilterPanel(
                isExpanded = uiState.filtersExpanded,
                availableTags = uiState.filtersAvailableTags,
                selectedTags = uiState.filtersSelectedTags,
                selectedLength = uiState.filtersSelectedLength,
                addTag = { distractionListViewModel.addFiltersSelectedTag(it) },
                removeTag = { distractionListViewModel.removeFiltersSelectedTag(it) },
                updateLength = { distractionListViewModel.updateFiltersSelectedLength(it) },
                updateExpansion = { distractionListViewModel.updateFiltersExpanded(it) },
        )
        LazyColumn {
            items(uiState.distractionList) {distraction ->
                ListedDistraction(
                    distraction = distraction,
                    onClick = { onDistractionClicked(distraction) }
                )
            }
        }
    }
}