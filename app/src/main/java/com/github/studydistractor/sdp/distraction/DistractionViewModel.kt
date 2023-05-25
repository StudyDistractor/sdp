package com.github.studydistractor.sdp.distraction

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.bookmark.BookmarkModel
import com.github.studydistractor.sdp.bookmark.BookmarkServiceFirebase
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.data.HistoryEntry
import com.github.studydistractor.sdp.history.HistoryModel
import com.github.studydistractor.sdp.history.HistoryServiceFirebase
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
    private val bookmarksService : BookmarkModel = BookmarkServiceFirebase(),
    private val historyService: HistoryModel = HistoryServiceFirebase()
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

    /**
     * Return true if the given distraction is bookmarked by the user else false
     */
    fun isBookmarked(distraction: Distraction): Boolean {
        return bookmarksService.isBookmarked(distraction)
    }

    /**
     * Act as a toggle for bookmark and handle bookmarked value
     */
    fun handleBookmark(): Task<Void> {
        return if(isBookmarked(_uiState.value.distraction)) {
            bookmarked = false
            bookmarksService.removeDistractionFromBookmark(_uiState.value.distraction)
        } else {
            bookmarked = true
            bookmarksService.addDistractionToBookmark(_uiState.value.distraction)
        }
    }

    /**
     * Update the UI each time the bookmark value is changed
     */
    fun onChangedBookmark() {
        if(bookmarked) {
            Log.d("boomarked", "update UI to true")
            _uiState.update { it.copy(isBookmarked = true) }
        } else {
            Log.d("boomarked", "update UI to false")
            _uiState.update { it.copy(isBookmarked = false) }
        }
    }

    /**
     * helper function that inverts the value of bookmarked
     */
    fun reverseBookmarked() {
        bookmarked = !bookmarked
    }

    /**
     * Add the current displayed distraction to the user's history
     *
     * return false in case the user is not logged in
     */
    fun distractionCompleted(): Boolean {
        val distraction = _uiState.value.distraction
        if (historyService.getCurrentUid() == null) return false
        return historyService.addHistoryEntry(
            entry = HistoryEntry(
                name = distraction.name!!,
                description = distraction.description!!,
                date = currentTimeToLong()
            ),
            uid = historyService.getCurrentUid()!!
        )
    }

    /**
     * Helper function to get the current time in a `Long` format
     */
    private fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }
}
