package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import com.github.studydistractor.sdp.distraction.Distraction
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
    distractionListViewModel.allDistractions()
    val distractions = distractionListViewModel.distractions
    distractionListViewModel.fetchDistractionsData()
    val uiState by distractionListViewModel.uiState.collectAsState()

    Column {
        DistractionListFilterPanel(
            isExpanded = uiState.filtersExpanded,
            availableTags = uiState.filtersAvailableTags,
            selectedTags = uiState.filtersSelectedTags,
            selectedLength = uiState.filtersSelectedLength,
            bookmarksOnly = uiState.bookmarksOnly,
            addTag = { distractionListViewModel.addFiltersSelectedTag(it) },
            removeTag = { distractionListViewModel.removeFiltersSelectedTag(it) },
            updateLength = { distractionListViewModel.updateFiltersSelectedLength(it) },
            updateExpansion = { distractionListViewModel.updateFiltersExpanded(it) },
            updateBookmarksOnly = {distractionListViewModel.updateBookmarksFilter(it)}
        )
        LazyColumn {
            Log.d("list of distraction size", uiState.distractionList.size.toString())
            items(uiState.distractionList) {distraction->
                ListedDistraction(
                    distraction = distraction,
                    onClick = { onDistractionClicked(distraction) },
                    isBookmarked = { distractionListViewModel.isBookmarked(it) }
                )
            }
        }
    }

}
