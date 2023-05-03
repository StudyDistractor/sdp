package com.github.studydistractor.sdp.distraction

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.bookmark.BookmarkModel
import com.github.studydistractor.sdp.bookmark.FirebaseBookmarks
import com.github.studydistractor.sdp.ui.state.DistractionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This class help passing distraction data between views
 */
class DistractionViewModel(
    private val bookmarksService : BookmarkModel = FirebaseBookmarks()
) : ViewModel(){

    private val _uiState = MutableStateFlow(DistractionUiState())
    val uiState: StateFlow<DistractionUiState> = _uiState.asStateFlow()

    /**
     * Add distraction
     *
     * @param distraction distraction to be added
     */
    fun updateDistraction(distraction: Distraction) {
        _uiState.update { it.copy(distraction = distraction) }
        if(isBookmarked(distraction)) {
            Log.d("Bookmark update", "value is true")
            _uiState.update { it.copy(isBookmarked = true) }
        } else {
            Log.d("Bookmark update", "value is false")
            _uiState.update { it.copy(isBookmarked = false) }
        }
    }

    fun isBookmarked(distraction: Distraction): Boolean {
        return bookmarksService.isBookmarked(distraction)
    }

    fun handleBookmark() {
        if(isBookmarked(_uiState.value.distraction)) {
            Log.d("Bookmark update", "removed")
            bookmarksService.removeDistractionFromBookmark(_uiState.value.distraction)
            _uiState.update { it.copy(isBookmarked = false) }
        } else {
            Log.d("Bookmark update", "added")
            bookmarksService.addDistractionToBookmark(_uiState.value.distraction)
            _uiState.update { it.copy(isBookmarked = true) }
        }
    }
}