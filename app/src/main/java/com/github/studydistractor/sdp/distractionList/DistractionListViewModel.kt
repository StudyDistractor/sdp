package com.github.studydistractor.sdp.distractionList

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.bookmark.BookmarkModel
import com.github.studydistractor.sdp.bookmark.BookmarkServiceFirebase
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.ui.state.DistractionListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DistractionListViewModel(
    private val distractionListModel: DistractionListModel = DistractionListServiceFirebase("ProcrastinationActivities", "Tags"),
    private val bookmarkService: BookmarkModel = BookmarkServiceFirebase("Bookmarks"),
) : ViewModel(){

    private val _uiState = MutableStateFlow(DistractionListUiState())
    val uiState: StateFlow<DistractionListUiState> = _uiState.asStateFlow()

    private val _distractions = mutableStateListOf<Distraction>()
    val distractions: List<Distraction> = _distractions

    init {
        _distractions.addAll(distractionListModel.getAllDistractions())
        Log.d("init", "init to distraction list view model")
        fetchDistractionsData()
        _uiState.update {
            it.copy(filtersAvailableTags = distractionListModel.getTags())
        }
    }

    fun filterDistractions() {
        var filteredDistractions = distractionListModel.getAllDistractions().toList()

        if(uiState.value.filtersSelectedLength != null) {
            filteredDistractions = filteredDistractions.filter {it.length == uiState.value.filtersSelectedLength}
            Log.d("filter", filteredDistractions.size.toString())
            Log.d("filter", uiState.value.filtersSelectedLength.toString())
        }

        if(uiState.value.filtersSelectedTags.isNotEmpty()) {
            for(tag in distractionListModel.getTags()) {
                filteredDistractions = filteredDistractions.filter { it.tags?.containsAll(uiState.value.filtersSelectedTags)
                    ?: false }
            }
        }

        if(uiState.value.bookmarksOnly) {
            filteredDistractions = filteredDistractions.filter { bookmarkService.isBookmarked(it) }
        }

        _distractions.clear()
        _distractions.addAll(filteredDistractions)
    }

    fun allDistractions() {
        _distractions.clear()
        _distractions.addAll(distractionListModel.getAllDistractions().toList())
    }

    fun fetchDistractionsData() {
        allDistractions()
        filterDistractions()
        _uiState.update {
            it.copy(distractionList = distractions)
        }
        _uiState.update {
            it.copy(filtersAvailableTags = distractionListModel.getTags())
        }
    }

    fun updateFiltersSelectedLength(length: Distraction.Length) {
        if(_uiState.value.filtersSelectedLength == length) {
            _uiState.update { it.copy(filtersSelectedLength = null) }
        } else {
            _uiState.update { it.copy(filtersSelectedLength = length) }
        }
    }

    fun addFiltersSelectedTag(tag: String) {
        _uiState.update {
            it.copy(filtersSelectedTags = uiState.value.filtersSelectedTags.plus(tag))
        }
    }

    fun removeFiltersSelectedTag(tag: String) {
        _uiState.update {
            it.copy(filtersSelectedTags = uiState.value.filtersSelectedTags.minus(tag))
        }
    }

    fun updateFiltersExpanded(expanded: Boolean) {
        _uiState.update {
            it.copy(filtersExpanded = expanded)
        }
    }

    fun isBookmarked(distraction: Distraction): Boolean {
        return bookmarkService.isBookmarked(distraction)
    }

    fun updateBookmarksFilter(bookmarksOnly: Boolean) {
        _uiState.update {
            it.copy(bookmarksOnly = bookmarksOnly)
        }
    }
}