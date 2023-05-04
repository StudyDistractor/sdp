package com.github.studydistractor.sdp.distraction

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.bookmark.BookmarkModel
import com.github.studydistractor.sdp.bookmark.BookmarkServiceFirebase
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.ui.state.DistractionUiState
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This class help passing distraction data between views
 */
class DistractionViewModel(
    private val bookmarksService : BookmarkModel = BookmarkServiceFirebase()
) : ViewModel(){

    private val _uiState = MutableStateFlow(DistractionUiState())
    val uiState: StateFlow<DistractionUiState> = _uiState.asStateFlow()
    private var bookmarked = false

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

    fun handleBookmark(): Task<Void> {
        return if(isBookmarked(_uiState.value.distraction)) {
            bookmarked = false
            bookmarksService.removeDistractionFromBookmark(_uiState.value.distraction)
        } else {
            bookmarked = true
            bookmarksService.addDistractionToBookmark(_uiState.value.distraction)
        }
    }

    fun onChangedBookmark() {
        if(bookmarked) {
            Log.d("boomarked", "update UI to true")
            _uiState.update { it.copy(isBookmarked = true) }
        } else {
            Log.d("boomarked", "update UI to false")
            _uiState.update { it.copy(isBookmarked = false) }
        }
    }

    fun reverseBookmarked() {
        bookmarked = !bookmarked
    }
}
